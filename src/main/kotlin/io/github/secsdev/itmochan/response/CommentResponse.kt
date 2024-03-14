package io.github.secsdev.itmochan.response

import io.github.secsdev.itmochan.entity.Comment
import io.github.secsdev.itmochan.entity.FilesIds

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
