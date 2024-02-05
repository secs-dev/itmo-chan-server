package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.entity.PollDTO
import io.github.e1turin.itmochan.response.CommentId
import io.github.e1turin.itmochan.service.CommentService
import io.github.e1turin.itmochan.service.FileService
import io.github.e1turin.itmochan.service.PollService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService,
    private val fileService: FileService,
    private val pollService: PollService,
) {

    @PostMapping
    fun addComment(
        @RequestPart("comment") comment: CommentDTO,
        @RequestPart("files") files : List<MultipartFile>?,
        @Valid @RequestPart("poll") poll: PollDTO?,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): CommentId {
        val fileIds = fileService.store(files)
        val commentId = commentService.addComment(comment, userDetails.username)
        fileService.linkFileIdAndCommentId(commentId, fileIds)
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
