package io.github.e1turin.itmochan.entity

data class Thread(
    val threadId : Int,
    val topicId : Int,
    val initCommentId : Int,
    val popularity : Int,
)

data class ThreadDTO(
    val topicId : Int,
)
