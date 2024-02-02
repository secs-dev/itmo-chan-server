package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.response.CommentId
import io.github.e1turin.itmochan.service.CommentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PutMapping
    fun addComment(
        @RequestBody comment: CommentDTO,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): CommentId {
        return CommentId(commentService.addComment(comment, userDetails.username))
    }

    @DeleteMapping("{commentId}")
    fun deleteComment(
        @PathVariable("commentId") commentId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        commentService.deleteComment(commentId, userDetails.username)
    }
}
