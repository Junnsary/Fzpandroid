package com.xhr.fzp.logic.dao

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.xhr.fzp.FzpApplication
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object ExternalStorageDao {

    const val USER_AVATAR = "user_avatar"

    private val externalFilesPicture = FzpApplication.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    fun savePicture(fileName: String, pic: Bitmap) {
        val fos = FileOutputStream(File(externalFilesPicture, fileName))
        pic.compress(Bitmap.CompressFormat.JPEG, 80, fos)
        fos.flush()
        fos.close()
    }

    fun isLocalUserAvatar() : Boolean {
        val file = File(externalFilesPicture, USER_AVATAR)
        return file.exists()
    }

    fun getLocalUserAvatar(): Bitmap {
        val fis = FileInputStream(File(externalFilesPicture, USER_AVATAR))
        val bitmap = BitmapFactory.decodeStream(fis)
        fis.close()
        return bitmap
    }

    fun clearLocalUserAvatar() {
        val userAvatar = File(externalFilesPicture, USER_AVATAR)
        userAvatar.delete()
    }
}