package com.xhr.fzp.ui.personal

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityPersonalBinding
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.utils.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream


class PersonalActivity : BaseActivity<ActivityPersonalBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[PersonalViewModel::class.java] }
    private var avatarFile: File? = null
    private var nameFlag: Boolean = false
    private var emailFlag: Boolean = false
    private lateinit var loadingDialog: LoadingDialog
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // User allow the permission.
//            "允许权限访问".showToast()
            openGallery()
        } else {
            // User deny the permission.
            "拒绝权限访问".showToast()
        }
    }

    fun getFileFromUri(uri: Uri, context: Context): File? {
        var filePath: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
                cursor.close()
            }
        } else if (uri.scheme == "file") {
            filePath = uri.path
        }

        return filePath?.let { File(it) }
    }

    private val requestDataLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                    val fileFromUri = getFileFromUri(intent.data!!, this)
                    fileFromUri?.let {
//                        LogUtil.d(this, fileFromUri.exists().toString())
                        binding.ivAvatar.setImageBitmap(
                            BitmapFactory.decodeStream(
                                (FileInputStream(
                                    it
                                ))
                            )
                        )
                        avatarFile = it
                    }
                }
                // Handle data from SecondActivity
            }
        }

    override fun initView() {
        setToolbar(binding.tlPersonal, "个人信息")


        //设置个人信息
        setUserInfo()
    }

    override fun initData() {
        viewModel.editUserLD.observe(this) { result ->
            result.onSuccess { succ ->
                "保存成功！".showToast()
                LogUtil.d(this, succ.data)
                saveSuccess(succ.data)
            }
            result.onFailure { fail->
//                LogUtil.d(this, fail.message.toString())
                "保存失败".showToast()
            }
            loadingDialog.dismiss()
        }
    }


    private fun openGallery() {
        val intent = if (Build.VERSION.SDK_INT >= 33) {
            Intent(MediaStore.ACTION_PICK_IMAGES)
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        requestDataLauncher.launch(intent)
    }


    private fun setUserInfo() {
        val user = viewModel.getSavedUser()
        binding.tvUserName.text = user.name
        binding.tvUserId.text = user.id
        binding.tvUserEmail.text = user.email
        val localAvatar = viewModel.getLocalAvatar()
        binding.ivAvatar.setImageBitmap(localAvatar)
    }

    override fun initListener() {
        binding.tvEditUserName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nameFlag = true
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.tvEditUserEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailFlag = true
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.tlPersonal.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_personal_modify -> {
                    edit()
                }

                R.id.action_personal_cancel -> {
                    cancel()
                }

                R.id.action_personal_done -> {
                    save()
                }
            }
            true
        }

        binding.rlModifyAvatar.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 33) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_personal_modify, menu)
        menu?.let {
            it.findItem(R.id.action_personal_done).isVisible = false
            it.findItem(R.id.action_personal_cancel).isVisible = false
        }
        return true
    }


    private fun edit() {
        binding.tlPersonal.menu.findItem(R.id.action_personal_done).isVisible = true
        binding.tlPersonal.menu.findItem(R.id.action_personal_cancel).isVisible = true
        binding.rlModifyAvatar.visibility = View.VISIBLE
        binding.tvEditUserEmail.visibility = View.VISIBLE
        binding.tvEditUserName.visibility = View.VISIBLE
        binding.tvEditUserEmail.setText(binding.tvUserEmail.text.toString())
        binding.tvEditUserName.setText(binding.tvUserName.text.toString())
        nameFlag = false
        emailFlag = false
        binding.tvUserEmail.visibility = View.GONE
        binding.tvUserName.visibility = View.GONE
        binding.tlPersonal.menu.findItem(R.id.action_personal_modify).isVisible = false
    }

    private fun save() {
        if (!nameFlag && !emailFlag && avatarFile == null) {
            cancel()
            "无修改信息".showToast()
        } else {
            val name = binding.tvEditUserName.text.toString().trim()
            val email = binding.tvEditUserEmail.text.toString().trim()
            val verify = ArrayList<String>()
//        LogUtil.d(this, email)
            if (!isEmailFormat(email)) {
                verify.add("邮箱格式不正确")
            }

            if (name.isEmpty()) {
                verify.add("请输入用户姓名")
            }

            if (verify.isEmpty()) {
                val user = viewModel.getSavedUser()
                if (avatarFile == null) {
                    viewModel.editUser(PersonalViewModel.EditUserInfo(null, name, email, user.id) )
                } else {
                    val avatar = MultipartBody.Part.createFormData("avatar", avatarFile!!.name, RequestBody.create(
                        MediaType.parse("multipart/form-data"), avatarFile!!))
                    viewModel.editUser(PersonalViewModel.EditUserInfo(avatar, name, email, user.id) )
                }
                loadingDialog = LoadingDialog(this, "正在保存")
                loadingDialog.show()
            } else {
                val sb = StringBuilder()
                verify.forEach {
                    sb.append("${it}\n")
                }
                val dialog = createDialog(sb.toString())
                dialog.show()
            }
        }
    }

    private fun cancel() {
        binding.tlPersonal.menu.findItem(R.id.action_personal_done).isVisible = false
        binding.tlPersonal.menu.findItem(R.id.action_personal_cancel).isVisible = false
        binding.tlPersonal.menu.findItem(R.id.action_personal_modify).isVisible = true
        binding.rlModifyAvatar.visibility = View.GONE
        binding.tvUserEmail.visibility = View.VISIBLE
        binding.tvUserName.visibility = View.VISIBLE
        binding.tvEditUserEmail.visibility = View.GONE
        binding.tvEditUserName.visibility = View.GONE
        setUserInfo()
    }

    private fun saveSuccess(data: String) {
        binding.tlPersonal.menu.findItem(R.id.action_personal_done).isVisible = false
        binding.tlPersonal.menu.findItem(R.id.action_personal_cancel).isVisible = false
        binding.tlPersonal.menu.findItem(R.id.action_personal_modify).isVisible = true
        binding.rlModifyAvatar.visibility = View.GONE
        binding.tvUserEmail.visibility = View.VISIBLE
        binding.tvUserName.visibility = View.VISIBLE
        binding.tvEditUserEmail.visibility = View.GONE
        binding.tvEditUserName.visibility = View.GONE

        val newName = binding.tvEditUserName.text.toString()
        val newEmail = binding.tvEditUserEmail.text.toString()
        val user = viewModel.getSavedUser()
        viewModel.saveUser(User(user.id, newEmail, newName, "", user.avatar))

        binding.tvUserName.text = newName
        binding.tvUserEmail.text = newEmail

        if (avatarFile != null) {
            LogUtil.d(this, data)
            val a = BitmapFactory.decodeStream(FileInputStream(avatarFile))
            viewModel.saveUser(User(user.id, newEmail, newName, "", data))
            binding.ivAvatar.setImageBitmap(a)
            viewModel.setUserAvatar(a)
        }
    }
}