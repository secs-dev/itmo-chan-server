package io.github.secsdev.itmochan.controller

import io.github.secsdev.itmochan.entity.CommentDTO
import io.github.secsdev.itmochan.entity.PollDTO
import io.github.secsdev.itmochan.response.CommentId
import io.github.secsdev.itmochan.response.CommentResponse
import io.github.secsdev.itmochan.service.CommentService
import io.github.secsdev.itmochan.service.FilePreService
import io.github.secsdev.itmochan.service.PollService
import io.github.secsdev.itmochan.service.ReplyService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService,
    private val filePreService: FilePreService,
    private val pollService: PollService,
    private val replyService: ReplyService,
) {

    @PostMapping
    fun addComment(
        @RequestPart("comment") comment: CommentDTO,
        @RequestPart("files") files : List<MultipartFile>?,
        @Valid @RequestPart("poll") poll: PollDTO?,
        @RequestPart("repliedTo") repliedTo: List<Long>?,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): CommentId {
        val fileIds = filePreService.store(files)
        val commentId = commentService.addComment(comment, userDetails.username)
        filePreService.linkFileIdAndCommentId(commentId, fileIds)
        pollService.createPoll(commentId, poll)
        replyService.saveRepliesTo(commentId, repliedTo ?: emptyList())
        return CommentId(commentId)
    }

    @DeleteMapping("{commentId}")
    fun deleteComment(
        @PathVariable("commentId") commentId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        commentService.deleteComment(commentId, userDetails.username)
    }

    @GetMapping("{commentId}")
    fun getComment(
        @PathVariable("commentId") commentId: Long,
    ) : CommentResponse {
        return commentService.getCommentResponse(commentId)
    }
}
