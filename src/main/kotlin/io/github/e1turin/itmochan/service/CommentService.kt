package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Comment
import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.repository.CommentRepository
import io.github.e1turin.itmochan.exception.NoRightsException
import io.github.e1turin.itmochan.exception.NoSuchCommentException
import io.github.e1turin.itmochan.exception.NoSuchPollException
import io.github.e1turin.itmochan.response.CommentResponse
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository : CommentRepository,
    private val userService: UserService,
    private val replyService: ReplyService,
    private val fileService: FileService,
    private val pollService: PollService,
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

    fun getCommentResponse(commentId: Long) : CommentResponse {
        val comment = getComment(commentId)
        val username = userService.findUserByUserId(comment.userId)
        val replies = replyService.getRepliesToComment(commentId)
        val repliedTo = replyService.getCommentsIdsWhomReplied(commentId)
        val filesIds = fileService.getFilesIdsAttachedToComment(commentId)
        val pollId : Long? = try {
            pollService.getPollIdByCommentId(commentId)
        } catch (_ : NoSuchPollException) {
            null
        }
        return CommentResponse(
            comment,
            username.username,
            replies,
            repliedTo,
            filesIds,
            pollId,
        )
    }
    fun getCommentsByThreadId(threadId: Long, offset: Long, limit: Long?) : List<Comment> {
        return commentRepository.findCommentsByThreadId(threadId, offset, limit)
    }

    fun deleteComment(commentId: Long, username: String) {
        val comment = getComment(commentId)
        val user = userService.findUserByUsername(username)
        if (comment.userId != user.userId)
            throw NoRightsException("You have no rights to delete this comment")
        commentRepository.setDeleted(commentId, true)
    }
}