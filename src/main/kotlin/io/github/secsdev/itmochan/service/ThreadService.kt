package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.Thread
import io.github.secsdev.itmochan.entity.ThreadDTO
import io.github.secsdev.itmochan.response.ThreadComments
import io.github.secsdev.itmochan.response.ThreadInitComment

interface ThreadService {

    fun getThreadsByTopicId(topicId: Long): List<Thread>

    fun getThreadByThreadId(threadId: Long): Thread

    fun getThread(threadId: Long) : ThreadInitComment

    fun getThreadComments(threadId: Long, offset: Long, limit: Long?) : ThreadComments

    fun addThread(threadDTO: ThreadDTO): Long

    fun addInitCommentIdToThread(threadId: Long, commentId: Long)
}