package io.github.e1turin.itmochan.entity

data class Thread(
    val threadId : Long,
    val topicId : Long,
    val initCommentId : Long,
    val popularity : Int,
)

data class ThreadDTO(
    val topicId : Long,
)
