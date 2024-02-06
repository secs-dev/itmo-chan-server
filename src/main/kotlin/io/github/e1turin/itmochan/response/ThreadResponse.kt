package io.github.e1turin.itmochan.response

import io.github.e1turin.itmochan.entity.Thread

data class ThreadComments(
    val thread: Thread,
    val comments: List<CommentResponse>,
)

data class ThreadInitComment(
    val thread: Thread,
    val initComment: CommentResponse?,
)
