package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.Thread
import io.github.secsdev.itmochan.entity.ThreadDTO
import io.github.secsdev.itmochan.repository.ThreadRepository
import io.github.secsdev.itmochan.response.ThreadComments
import io.github.secsdev.itmochan.response.ThreadInitComment
import io.github.secsdev.itmochan.exception.NoSuchThreadException
import io.github.secsdev.itmochan.service.CommentService
import io.github.secsdev.itmochan.service.ThreadService
import org.springframework.stereotype.Service

@Service
class ThreadServiceImpl(
    private val threadRepository : ThreadRepository,
    private val commentService: CommentService,
): ThreadService {
    override fun getThreadsByTopicId(topicId: Long): List<Thread> {
        return threadRepository.findAllByTopicId(topicId) //TODO add offset and limit
    }

    override fun getThreadByThreadId(threadId: Long): Thread {
        val thread = threadRepository.findThreadByThreadId(threadId)
        if (thread.isEmpty)
            throw NoSuchThreadException("Thread $threadId doesn't exists")
        return thread.get()
    }

    override fun getThread(threadId: Long) : ThreadInitComment {
        val thread = getThreadByThreadId(threadId)
        val initComment =
            if (thread.initCommentId == null)
                null
            else
                commentService.getCommentResponse(thread.initCommentId)
        return ThreadInitComment(thread, initComment)
    }

    override fun getThreadComments(threadId: Long, offset: Long, limit: Long?) : ThreadComments {
        val thread = getThreadByThreadId(threadId)
        val comments = commentService.getCommentsByThreadId(threadId, offset, limit)
        val commentsResponse = comments.map {
            commentService.getCommentResponse(it.commentId)
        }
        return ThreadComments(thread, commentsResponse)
    }

    override fun addThread(threadDTO: ThreadDTO): Long {
        return threadRepository.saveThread(threadDTO.topicId)
    }

    override fun addInitCommentIdToThread(threadId: Long, commentId: Long) {
        threadRepository.setInitCommentId(threadId, commentId)
    }
}