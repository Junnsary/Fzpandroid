package com.xhr.fzp.logic.dao

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.xhr.fzp.FzpApplication
import java.io.*

object ExternalStorageDao {

    const val USER_AVATAR = "user_avatar"

    private val externalFilesPicture = FzpApplication.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    private val externalCacheImageDir = File(FzpApplication.context.externalCacheDir, "Images")

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
        if (userAvatar.exists()) {
            userAvatar.delete()
        }
    }

    fun cachedImages(imageName: String, inputStream: InputStream){
        /**
         * 1. 首先获得缓存土图片目录
         * 2. 需要图片的名字和二进制流
         * 3. 保存就可以了
         */
        if (!externalCacheImageDir.exists()) {
            externalCacheImageDir.mkdir()
        }
        val image = File(externalCacheImageDir, imageName)
        val fos = FileOutputStream(image)
        val bis = BufferedInputStream(inputStream)
        bis.use {
            val buff = ByteArray(1024)
            var length = 0
            do {
                length = it.read(buff)
                fos.write(buff, 0, length)
            }while (length != -1)
        }
        fos.flush()
        fos.close()
    }
    fun cachedImagesByBitmap(imageName: String, bitmap: Bitmap){
        /**
         * 1. 首先获得缓存土图片目录
         * 2. 需要图片的名字和二进制流
         * 3. 保存就可以了
         */
        if (!externalCacheImageDir.exists()) {
            externalCacheImageDir.mkdir()
        }
        val fos = FileOutputStream(getCachedImagesPath(imageName))
        fos.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, it)
        }
    }

    fun isCachedImages(imageName: String) : Boolean {
        /**
         * 1. 检查缓存的目录是否存在，不存在就直接返回false
         * 2. 开始检查是否有文件
         */
        if (!externalCacheImageDir.exists()) {
            return false
        }
        val image = File(externalCacheImageDir, imageName)
        return image.exists()
    }

    fun readCachedImages(imageName: String): Bitmap {
        /**
         * 1. 读取文件的话就肯定是存在的了
         * 2. 根据文件名字读取
         */
        val fis = FileInputStream(File(externalCacheImageDir, imageName))
        val bis = BufferedInputStream(fis)
//        CoroutineScope(Dispatchers.IO).launch {
//
//        }
//        val file = File(externalCacheImageDir, imageName)
//        return BitmapFactory.decodeFile(file.absolutePath)
        return BitmapFactory.decodeStream(bis)
    }

    fun getCachedImagesPath(imageName: String) : String {
        return File(externalCacheImageDir, imageName).toString()
    }

}