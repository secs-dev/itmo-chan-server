package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.response.CommentId
import io.github.e1turin.itmochan.service.CommentService
import io.github.e1turin.itmochan.service.FileService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService,
    private val fileService: FileService,
) {

    @PostMapping
    fun addComment(
        @RequestPart("comment") comment: CommentDTO,
        @RequestPart("files") files : List<MultipartFile>?,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): CommentId {
        val fileIds = fileService.store(files)
        val commentId = commentService.addComment(comment, userDetails.username)
        fileService.linkFileIdAndCommentId(commentId, fileIds)
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
