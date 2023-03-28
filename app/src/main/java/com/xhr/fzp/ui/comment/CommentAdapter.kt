package com.xhr.fzp.ui.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xhr.fzp.R
import com.xhr.fzp.logic.model.Comment
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.utils.useGlideSetImage

class CommentAdapter(private val fragment: Fragment, private val commentList: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userAvatar: ImageView = view.findViewById(R.id.iv_comment_user_avatar)
        val userName: TextView = view.findViewById(R.id.tv_comment_name)
        val commentDate: TextView = view.findViewById(R.id.tv_comment_date)
        val commentContent: TextView = view.findViewById(R.id.tv_comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.commentContent.text = comment.content
        holder.commentDate.text = comment.createdAt.toString()
        holder.userName.text = comment.user.name
        useGlideSetImage(fragment, FzpServiceCreator.getUserAvatarUrl(comment.user.avatar), holder.userAvatar)
    }

}