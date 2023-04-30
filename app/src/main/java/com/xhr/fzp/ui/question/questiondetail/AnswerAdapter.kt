package com.xhr.fzp.ui.question.questiondetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xhr.fzp.R
import com.xhr.fzp.logic.model.Answer
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.formatDateTime
import com.xhr.fzp.utils.useGlideSetImage

class AnswerAdapter(private val answerList: List<Answer>, private val context: Context): RecyclerView.Adapter<AnswerAdapter.AnswerHolder>() {

    inner class  AnswerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answerUserAvatar: ImageView = view.findViewById(R.id.iv_comment_user_avatar)
        val answerUserName: TextView = view.findViewById(R.id.tv_comment_name)
        val answerDate: TextView = view.findViewById(R.id.tv_comment_date)
        val answerContent: TextView = view.findViewById(R.id.tv_comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return AnswerHolder(view)
    }

    override fun getItemCount() = answerList.size

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        val answer = answerList[position]
        holder.answerContent.text = answer.content
        holder.answerDate.text = formatDateTime(answer.createdAt)
        holder.answerUserName.text = answer.user.name
        LogUtil.d(this, answer.user.avatar)
        useGlideSetImage(context, FzpServiceCreator.getUserAvatarUrl(answer.user.avatar), holder.answerUserAvatar)
    }

}