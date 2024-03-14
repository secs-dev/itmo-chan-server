package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.Comment
import io.github.secsdev.itmochan.entity.CommentDTO
import io.github.secsdev.itmochan.repository.CommentRepository
import io.github.secsdev.itmochan.exception.NoRightsException
import io.github.secsdev.itmochan.exception.NoSuchCommentException
import io.github.secsdev.itmochan.exception.NoSuchPollException
import io.github.secsdev.itmochan.response.CommentResponse
import io.github.secsdev.itmochan.service.CommentService
import io.github.secsdev.itmochan.service.FileService
import io.github.secsdev.itmochan.service.PollService
import io.github.secsdev.itmochan.service.ReplyService
import io.github.secsdev.itmochan.service.UserService
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository : CommentRepository,
    private val userService: UserService,
    private val replyService: ReplyService,
    private val fileService: FileService,
    private val pollService: PollService,
): CommentService {
    override fun addComment(commentDTO : CommentDTO, username: String) : Long {
        val userId = userService.findUserByUsername(username).userId
        return commentRepository.saveComment(commentDTO.threadId, commentDTO.title, commentDTO.content, userId)
    }

    override fun getComment(commentId: Long) : Comment {
        val comment = commentRepository.findCommentByCommentId(commentId)
        if (comment.isEmpty)
            throw NoSuchCommentException("Comment $commentId doesn't exists")
        return comment.get()
    }

    override fun getCommentResponse(commentId: Long) : CommentResponse {
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
    override fun getCommentsByThreadId(threadId: Long, offset: Long, limit: Long?) : List<Comment> {
        return commentRepository.findCommentsByThreadId(threadId, offset, limit)
    }

    override fun deleteComment(commentId: Long, username: String) {
        val comment = getComment(commentId)
        val user = userService.findUserByUsername(username)
        if (comment.userId != user.userId)
            throw NoRightsException("You have no rights to delete this comment")
        commentRepository.setDeleted(commentId, true)
    }
}