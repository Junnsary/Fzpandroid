package com.xhr.fzp.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xhr.fzp.FzpApplication
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.logic.dao.ExternalStorageDao
import com.xhr.fzp.logic.model.Manager
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.logic.model.Tag
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.logic.room.entity.Save
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作为字符串对象的扩展函数showToast，就可以快捷显示Toast了
 * @param duration Toast显示的时间长短
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(FzpApplication.context, this, duration).show()
}

/**
 * 同样更字符串对象扩展函数一样，作为Int对象的扩展函数
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(FzpApplication.context, this, duration).show()
}

/**
 * 为布局快捷替换fragment对象的扩展函数函数
 */
fun <T : ViewBinding> BaseActivity<T>.replaceFragment(id: Int, fragment: Fragment) {
//    val sfm = supportFragmentManager
//    val bt = sfm.beginTransaction()
//    bt.replace(id, fragment)
//    bt.commit()
    supportFragmentManager.commit { replace(id, fragment) }
}

fun <T : ViewBinding> BaseFragment<T>.replaceFragment(id: Int, fragment: Fragment) {
    activity?.let {
        val sfm = it.supportFragmentManager
        val bt = sfm.beginTransaction()
        bt.replace(id, fragment)
        bt.commit()
    }
}


fun Context.createDialog(msg: String) =
    AlertDialog.Builder(this).run {
        setTitle("提示")
        setMessage(msg)
        show()
    }


/**
 * 填充图片
 */
fun useGlideSetImage(fragment: Fragment, url: String, imageView: ImageView) {
    Glide
        .with(fragment)
        .load(url)
        .into(imageView)
}

/**
 * 快捷启动activity
 */

inline fun <reified T> Context.quickStartActivity() {
    startActivity(Intent(this, T::class.java))
}

/**
 * save 转换 source
 */

fun saveTurnToData(saves: List<Save>): List<Source> {
    val data = ArrayList<Source>()
    saves.forEach {
        /**
         * 封面还是用 字符串存储，然后在本地先搜索图片，没有再去加载网络图片
         */
        val temp = Source(
            it.id,
            it.title,
            Date(it.date),
            Date(it.date),
            Manager("", it.managerName),
            "",
            Tag(it.tagId, "", it.type, ""),
            it.cover
        )
        data.add(temp)
    }
    return data
}

fun dataTurnToSave(data: List<Source>, category: String, name: String): List<Save> {
    val saves = ArrayList<Save>()
    data.forEach {
        val save = Save(
            it.id,
            it.tag.id,
            it.tag.type,
            it.title,
            it.cover,
            it.manager.name,
            it.createdAt.time,
            category,
            name
        )
        saves.add(save)
    }
    return saves
}


fun setCoverImageOfSource(imageName: String, imageView: ImageView, context: Context) {
    /**
     * 1. 查看 本地是否已经缓存 图片，
     *  有则直接加载，
     *  没有就换网络加载,并且保存在本地里，以便下次直接读取
     *
     */
    if (ExternalStorageDao.isCachedImages(imageName)) {
        //存在就获取本地缓存的
        CoroutineScope(Dispatchers.Main).launch {
            LogUtil.d("setCoverImageOfSource", "缓存存在图片")
            /**
             * Bitmap image = BitmapFactory.decodeFile(file.getPath());
            float w = image.getWidth();//get width
            float h = image.getHeight();//get height
            int W = [handle width management here...];
            int H = (int) ( (h*W)/w);
            Bitmap b = Bitmap.createScaledBitmap(image, W, H, false);//scale the bitmap
            imageView.setImageBitmap(b);//set the image view
            image = null;//save memory on the bitmap called 'image'
             */
            val image = ExternalStorageDao.readCachedImages(imageName)
            imageView.setImageBitmap(image)
        }
    } else {
        //不存在就获取网络图片并且保存在本地
        Glide.with(context)
            .asBitmap()
            .load(FzpServiceCreator.getNetworkImageURL(imageName))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // 保存前进行裁剪， 取出的时候就不用操作了
                    val w = resource.width
                    val h = resource.height
                    val W = 600
                    val H = (h * W) / w
                    val b = Bitmap.createScaledBitmap(resource, W, H, false)
                    imageView.setImageBitmap(b)
                    //保存在本地 bitmap在本地
                    ExternalStorageDao.cachedImagesByBitmap(imageName,b)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}

/**
 * 设置toolbar的返回按钮
 */

fun AppCompatActivity.setToolbar(toolbar: Toolbar, title : String = "") {
    setSupportActionBar(toolbar)
    supportActionBar?.let {
        it.title = title
        it.setDisplayHomeAsUpEnabled(true)
    }
}

/**
 * 格式化日期
 */

fun formatDate(date: Date) : String {
    val sdf = SimpleDateFormat("YYYY-MM-dd")
    return sdf.format(date.time)
}

fun formatDateTime(date: Date) : String {
    val sdf = SimpleDateFormat("YYYY-MM-dd HH:mm")
    return sdf.format(date.time)
}

fun <T> LiveData<Result<T>>.observeResult(lifecycleOwner: LifecycleOwner, onSuccess: (T) -> Unit, onFailure: (e: Throwable) -> Unit) {
    observe(lifecycleOwner) { result ->
        result.onSuccess {
            onSuccess(it)
        }
        result.onFailure {
            onFailure(it)
        }
    }
}