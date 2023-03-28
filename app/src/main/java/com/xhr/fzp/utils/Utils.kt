package com.xhr.fzp.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.xhr.fzp.FZPApplication
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.base.BaseFragment

/**
 * 作为字符串对象的扩展函数showToast，就可以快捷显示Toast了
 * @param duration Toast显示的时间长短
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(FZPApplication.context, this, duration).show()
}

/**
 * 同样更字符串对象扩展函数一样，作为Int对象的扩展函数
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(FZPApplication.context, this, duration).show()
}

/**
 * 为布局快捷替换fragment对象的扩展函数函数
 */
fun <T : ViewBinding> BaseActivity<T>.replaceFragment(id: Int, fragment: Fragment){
    val sfm = supportFragmentManager
    val bt = sfm.beginTransaction()
    bt.replace(id, fragment)
    bt.commit()
}

fun <T : ViewBinding> BaseFragment<T>.replaceFragment(id: Int, fragment: Fragment){
    activity?.let{
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
fun useGlideSetImage(fragment: Fragment, url: String, imageView: ImageView){
    Glide
        .with(fragment)
        .load(url)
        .into(imageView)
}