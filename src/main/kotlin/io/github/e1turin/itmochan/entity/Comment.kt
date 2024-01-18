package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("Comments")
data class Comment(
    val commentId : Long,
    val threadId : Long,
    val title : String?,
    val content : String,
    val userId : Long,
    val reactionId : Long,
    val creationDate : LocalDateTime,
    val trashed : Boolean,
    val deleted : Boolean,
)

@Table("Replies")
data class Reply(
    val commentId : Long,
    val replyCommentId : Long,
)

data class CommentDTO(
    val threadId : Long,
    val title : String?,
    val content : String,
    val userId : Long,
)
