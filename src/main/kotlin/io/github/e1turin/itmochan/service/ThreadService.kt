package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Thread
import io.github.e1turin.itmochan.entity.ThreadDTO
import io.github.e1turin.itmochan.repository.ThreadRepository
import io.github.e1turin.itmochan.response.ThreadComments
import io.github.e1turin.itmochan.response.ThreadInitComment
import io.github.e1turin.itmochan.exception.NoSuchThreadException
import org.springframework.stereotype.Service

@Service
class ThreadService(
    private val threadRepository : ThreadRepository,
    private val commentService: CommentService,
) {
    fun getThreadsByTopicId(topicId: Long): List<Thread> {
        return threadRepository.findAllByTopicId(topicId) //TODO add offset and limit
    }

    fun getThreadByThreadId(threadId: Long): Thread {
        val thread = threadRepository.findThreadByThreadId(threadId)
        if (thread.isEmpty)
            throw NoSuchThreadException("Thread $threadId doesn't exists")
        return thread.get()
    }

    fun getThread(threadId: Long) : ThreadInitComment {
        val thread = getThreadByThreadId(threadId)
        val initComment =
            if (thread.initCommentId == null)
                null
            else
                commentService.getCommentResponse(thread.initCommentId)
        return ThreadInitComment(thread, initComment)
    }

    fun getThreadComments(threadId: Long, offset: Long, limit: Long?) : ThreadComments {
        val thread = getThreadByThreadId(threadId)
        val comments = commentService.getCommentsByThreadId(threadId, offset, limit)
        val commentsResponse = comments.map {
            commentService.getCommentResponse(it.commentId)
        }
        return ThreadComments(thread, commentsResponse)
    }

    fun addThread(threadDTO: ThreadDTO): Long {
        return threadRepository.saveThread(threadDTO.topicId)
    }

    fun addInitCommentIdToThread(threadId: Long, commentId: Long) {
        threadRepository.setInitCommentId(threadId, commentId)
    }
}