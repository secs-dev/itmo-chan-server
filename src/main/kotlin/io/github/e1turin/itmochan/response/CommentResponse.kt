package io.github.e1turin.itmochan.response

import io.github.e1turin.itmochan.entity.Comment
import io.github.e1turin.itmochan.entity.FilesIds

data class CommentId(
    val commentId: Long,
)

data class CommentResponse(
    val comment: Comment,
    val username: String,
    val replies: List<Long>,
    val repliedTo: List<Long>,
    val filesIds: FilesIds,
    val pollId: Long?,
)
