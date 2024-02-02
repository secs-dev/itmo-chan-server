package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Comment
import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.repository.CommentRepository
import io.github.e1turin.itmochan.security.exception.NoSuchCommentException
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository : CommentRepository,
    private val userService: UserService,
) {
    fun addComment(commentDTO : CommentDTO, username: String) : Long {
        val userId = userService.findUserByUsername(username).userId
        return commentRepository.saveComment(commentDTO.threadId, commentDTO.title, commentDTO.content, userId)
    }

    fun getComment(commentId: Long) : Comment {
        val comment = commentRepository.findCommentByCommentId(commentId)
        if (comment.isEmpty)
            throw NoSuchCommentException("Comment $commentId doesn't exists")
        return comment.get()
    }
    fun getCommentsByThreadId(threadId: Long, offset: Long, limit: Long) : List<Comment> {
        return commentRepository.findCommentsByThreadId(threadId, offset, limit)
    }
}