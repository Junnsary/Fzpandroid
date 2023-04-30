package com.xhr.fzp.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xhr.fzp.R
import com.xhr.fzp.logic.model.Question
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.ui.question.questiondetail.QuestionDetailActivity
import com.xhr.fzp.utils.formatDateTime
import com.xhr.fzp.utils.useGlideSetImage
import de.hdodenhof.circleimageview.CircleImageView

class QuestionAdapter(private val questionList: List<Question>, private val fragment: Fragment) : RecyclerView.Adapter<QuestionAdapter.QuestionHolder>() {

    inner class QuestionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.findViewById(R.id.iv_question_user_avatar)
        val userName : TextView = view.findViewById(R.id.tv_question_user_name)
        val date: TextView = view.findViewById(R.id.tv_question_date)
        val content: TextView = view.findViewById(R.id.tv_question_content)
        val answerNum: TextView = view.findViewById(R.id.tv_answer_num)
        val answerShow : LinearLayout = view.findViewById(R.id.ll_answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionHolder(view)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        val question = questionList[position]
        when (question.review) {
            1 -> {
                holder.answerShow.visibility = View.VISIBLE
                holder.itemView.setOnClickListener {
                    fragment.activity?.let {
                        QuestionDetailActivity.actionStart(it, question)
                    }
                }
            }
            0, 2 -> {
                holder.answerShow.visibility = View.GONE
            }
        }
        holder.userName.text = question.user.name
        holder.answerNum.text = question.answerNum.toString()
        holder.content.text = question.content
        holder.date.text = formatDateTime(question.createdAt)
        useGlideSetImage(fragment.activity, FzpServiceCreator.getUserAvatarUrl(question.user.avatar), holder.avatar)
    }
}
