package io.github.e1turin.itmochan.entity

import java.time.LocalDateTime

data class Comment(
    val commentId : Int,
    val threadId : Int,
    val title : String?,
    val content : String,
    val userId : Int,
    val reactionId : Int,
    val creationDate : LocalDateTime,
    val trashed : Boolean,
    val deleted : Boolean,
)

data class Reply(
    val commentId : Int,
    val replyCommentId : Int,
)

data class CommentDTO(
    val threadId : Int,
    val title : String?,
    val content : String,
    val userId : Int,
)
