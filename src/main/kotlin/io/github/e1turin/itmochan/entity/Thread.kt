package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Threads")
data class Thread(
    val threadId : Long,
    val topicId : Long,
    val initCommentId : Long,
    val popularity : Int,
)

data class ThreadDTO(
    val topicId : Long,
)
