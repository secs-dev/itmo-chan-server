package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Comment
import io.github.e1turin.itmochan.entity.Thread
import io.github.e1turin.itmochan.entity.ThreadCommentDTO
import io.github.e1turin.itmochan.repository.ThreadRepository
import io.github.e1turin.itmochan.response.ThreadComments
import io.github.e1turin.itmochan.response.ThreadInitComment
import io.github.e1turin.itmochan.security.exception.NoSuchThreadException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

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
        val initComment: Comment? =
            if (thread.initCommentId == null)
                null
            else
                commentService.getComment(thread.initCommentId)
        return ThreadInitComment(thread, initComment)
    }

    fun getThreadComments(threadId: Long, offset: Long, limit: Long) : ThreadComments {
        val thread = getThreadByThreadId(threadId)
        val comments = commentService.getCommentsByThreadId(threadId, offset, limit)
        return ThreadComments(thread, comments)
    }

    fun addThread(threadCommentDTO: ThreadCommentDTO, username: String): ThreadInitComment {
        val threadId = threadRepository.saveThread(threadCommentDTO.thread.topicId)
        val commentId = commentService.addComment(threadCommentDTO.initComment.copy(threadId = threadId), username)
        threadRepository.setInitCommentId(threadId, commentId)
        return ThreadInitComment(
            threadRepository.findThreadByThreadId(threadId).get(),
            commentService.getComment(commentId))
    }
}