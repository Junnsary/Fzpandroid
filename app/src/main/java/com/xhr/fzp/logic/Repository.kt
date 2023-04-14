package com.xhr.fzp.logic

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.xhr.fzp.FzpApplication
import com.xhr.fzp.logic.dao.ExternalStorageDao
import com.xhr.fzp.logic.dao.UserDao
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.logic.network.FzpNetwork
import com.xhr.fzp.logic.room.database.AppDatabase
import com.xhr.fzp.logic.room.entity.Save
import com.xhr.fzp.ui.detail.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object Repository {

    private val dbHelper = AppDatabase.getDatabase(FzpApplication.context)

    /**
     * 方便创建livedata
     */
    private fun <T> fire(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> Result<T>
    ) =
        liveData(context) {
            val result = try {
                block()
//            } catch (ce: ConnectException) {
////                LogUtil.d(this, "网络发生错误")
//                Result.failure(ce)
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
//            emit(block())
        }

    /**
     * 获取文章
     */
    fun getArticleListByTag(tagId: String) = fire() {
        val articleList = FzpNetwork.getArticleListByTag(tagId)
        if (articleList.success) {
            val data = articleList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${articleList.message}"))
        }
    }

    /**
     * 获取视频
     */
    fun getVideoListByTag(tagId: Int) = fire {
        val videoList = FzpNetwork.getVideoListByTag(tagId)
        if (videoList.success) {
            val data = videoList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${videoList.success}"))
        }
    }

    /**
     * 获取标签
     */
    fun getTags(type: String, category: String) = fire() {
        val categoryList = FzpNetwork.getTags(type, category)
        if (categoryList.success) {
            val data = categoryList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${categoryList.success}"))
        }
    }

    fun getRecommList(num: Int) = fire() {
        val recommList = FzpNetwork.getRecommList(num)
        if (recommList.success) {
            val data = recommList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${recommList.success}"))
        }
    }

    fun getKnowledge() = fire() {
        val tagList = FzpNetwork.getKnowledge()
        if (tagList.success) {
            val data = tagList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${tagList.success}"))
        }
    }

    fun getSourceList(type: String, tagId: String) = fire() {
        val sourceList = FzpNetwork.getSourceList(type, tagId)
        if (sourceList.success) {
            val data = sourceList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${sourceList.success}"))
        }
    }

    fun userSignup(user: User) = fire() {
        val userResult = FzpNetwork.userSignup(user)
        Result.success(userResult)
    }

    fun userLogin(user: User) = fire() {
        val user = FzpNetwork.userLogin(user)
        Result.success(user)
    }

    fun getAvatar(fileName: String) = fire() {
        val imageResult = FzpNetwork.getAvatar(fileName)
        Result.success(imageResult)
    }

    fun saveUser(user: User) = UserDao.saveUser(user)

    fun getSavedUser() = UserDao.getSavedUser()

    fun isUserLogin() = UserDao.isUserLogin()

    fun clearUser() = UserDao.clearUser()

    fun saveUserAvatarToLocal(picture: Bitmap) {
        ExternalStorageDao.savePicture(ExternalStorageDao.USER_AVATAR, picture)
    }

    fun isLocalUserAvatar(): Boolean {
        return ExternalStorageDao.isLocalUserAvatar();
    }

    fun getUserAvatarFormLocal(): Bitmap {
        return ExternalStorageDao.getLocalUserAvatar()
    }

    fun clearLocalUserAvatar() = ExternalStorageDao.clearLocalUserAvatar()

    fun getCommentList(sourceId: Int, tagId: Int) = fire() {
        val commentList = FzpNetwork.getCommentList(sourceId, tagId)
        if (commentList.success) {
            val data = commentList.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${commentList.success}"))
        }
    }

    fun getVideoInfo(sourceId: Int) = fire() {
        val result = FzpNetwork.getVideoInfo(sourceId)
        if (result.success) {
            val data = result.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${result.success}"))
        }
    }

    fun isUserCollect(favoritesData: DetailViewModel.FavoritesData) = fire() {
        val result = FzpNetwork.isUserCollect(favoritesData)
        if (result.success) {
            val data = result.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${result.success}"))
        }
    }

    fun addUserFavorites(favoritesData: DetailViewModel.FavoritesData) = fire() {
        val result = FzpNetwork.addUserFavorites(favoritesData)
        Result.success(result)
    }

    fun cancelUserFavorites(favoritesData: DetailViewModel.FavoritesData) = fire() {
        val result = FzpNetwork.cancelUserFavorites(favoritesData)
        Result.success(result)
    }

    fun addUserComment(commentData: DetailViewModel.CommentData) = fire() {
        val result = FzpNetwork.addUserComment(commentData)
        Result.success(result)
    }

    fun getFavoritesList(userId: String) = fire() {
        val result = FzpNetwork.getFavoritesList(userId)
        if (result.success) {
            val data = result.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${result.success}"))
        }
    }

    fun saveSourceToDatabase(saves: List<Save>) {
        //开启协程
        CoroutineScope(Dispatchers.IO).launch {
            if (saves.isNotEmpty()) {
                dbHelper.saveDao().deleteSaveByPC(saves[0].category, saves[0].name)
                dbHelper.saveDao().insertSaveList(saves)
            }
        }
    }

    fun getSourceFromDatabase(
        category: String,
        name: String,
        type: Array<String>
    ): LiveData<List<Save>> {
        return dbHelper.saveDao().querySaveByPC(category, name, type)
    }

    fun deleteSourceFromDatabase(category: String, name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.saveDao().deleteSaveByPC(category, name)
        }
    }

    fun getAllCase() = fire {
        val result = FzpNetwork.getAllCase()
        if (result.success) {
            val data = result.data
            Result.success(data)
        } else {
            Result.failure(RuntimeException("response success is ${result.success}"))
        }
    }
}