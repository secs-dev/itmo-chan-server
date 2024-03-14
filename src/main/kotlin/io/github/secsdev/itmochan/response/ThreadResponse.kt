package io.github.secsdev.itmochan.response

import io.github.secsdev.itmochan.entity.Thread

data class ThreadComments(
    val thread: Thread,
    val comments: List<CommentResponse>,
)

data class ThreadInitComment(
    val thread: Thread,
    val initComment: CommentResponse?,
)
