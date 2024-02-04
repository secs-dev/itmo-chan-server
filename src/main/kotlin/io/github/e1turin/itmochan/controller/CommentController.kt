package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.entity.PollDTO
import io.github.e1turin.itmochan.response.CommentId
import io.github.e1turin.itmochan.service.CommentService
import io.github.e1turin.itmochan.service.PollService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService,
    private val pollService: PollService,
) {

    @PostMapping
    fun addComment(
        @RequestPart("comment") comment: CommentDTO,
        @Valid @RequestPart("poll") poll: PollDTO?,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): CommentId {
        val commentId = commentService.addComment(comment, userDetails.username)
        pollService.createPoll(commentId, poll)
        return CommentId(commentId)
    }

    @DeleteMapping("{commentId}")
    fun deleteComment(
        @PathVariable("commentId") commentId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        commentService.deleteComment(commentId, userDetails.username)
    }
}
