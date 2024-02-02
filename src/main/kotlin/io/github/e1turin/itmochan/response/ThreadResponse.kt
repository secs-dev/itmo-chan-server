package io.github.e1turin.itmochan.response

import io.github.e1turin.itmochan.entity.Comment
import io.github.e1turin.itmochan.entity.Thread

data class ThreadComments(
    val thread: Thread,
    val comments: List<Comment>,
)

data class ThreadInitComment(
    val thread: Thread,
    val initComment: Comment?,
)

data class ThreadId(
    val threadId: Long,
)
