package com.xhr.fzp.other

import com.xhr.fzp.logic.model.Message

fun main() {
    val map = HashMap<Int, String>()
    val stringList = listOf("文章", "视频", "全部")
    map[0] = stringList[0]
    map[1] = stringList[1]
    map[2] = stringList[2]

    val msg = HashMap<String, Message>()
    msg[stringList[0]] = Message("文章")
    msg[stringList[1]] = Message("视频")
    msg[stringList[2]] = Message("全部")

    for (i in 0 until 3) {
        println(msg[map[i]])
    }


}