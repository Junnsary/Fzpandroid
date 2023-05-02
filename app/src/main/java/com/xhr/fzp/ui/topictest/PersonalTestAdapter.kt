package com.xhr.fzp.ui.topictest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xhr.fzp.R
import com.xhr.fzp.logic.model.UserTopic
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.utils.formatDate

class PersonalTestAdapter(private val context: Context, private val userTopicList: List<UserTopic>) : RecyclerView.Adapter<PersonalTestAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date : TextView = view.findViewById(R.id.tv_personal_test_date)
        val score : TextView = view.findViewById(R.id.tv_personal_test_score)
        val sum : TextView = view.findViewById(R.id.tv_personal_test_sum)
        val right : TextView = view.findViewById(R.id.tv_personal_test_right)
        val error : TextView = view.findViewById(R.id.tv_personal_test_error)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_personal_test, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userTopicList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userTopic =  userTopicList[position]
        holder.date.text = formatDate(userTopic.created_at)
        holder.score.text = userTopic.score.toString()
        holder.sum.text = userTopic.topic_sum.toString()
        holder.right.text = userTopic.topic_right.toString()
        holder.error.text = userTopic.topic_error.toString()
        holder.itemView.setOnClickListener {
            WebShowActivity.actionStart(context, FzpServiceCreator.getUserTopicDetailUrl(userTopic.id), "自测详细")
        }
    }
}