package io.github.secsdev.itmochan.controller

import io.github.secsdev.itmochan.entity.CommentDTO
import io.github.secsdev.itmochan.entity.PollDTO
import io.github.secsdev.itmochan.entity.ThreadDTO
import io.github.secsdev.itmochan.response.ThreadComments
import io.github.secsdev.itmochan.response.ThreadInitComment
import io.github.secsdev.itmochan.service.ThreadService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/thread")
class ThreadController(
    private val threadService: ThreadService,
    private val commentController: CommentController,
) {

    @GetMapping("{threadId}")
    fun getThread(
        @PathVariable("threadId") threadId: Long,
    ): ThreadInitComment {
        return threadService.getThread(threadId)
    }
    @GetMapping("{threadId}/comments")
    fun getThreadComments(
        @PathVariable("threadId") threadId: Long,
        offset: Long?,
        @Min(0) limit: Long?,
    ): ThreadComments {
        return threadService.getThreadComments(threadId, offset ?: 0, limit)
    }

    @PostMapping
    fun addThread(
        @RequestPart("thread") thread: ThreadDTO,
        @RequestPart("comment") comment: CommentDTO,
        @RequestPart("files") files : List<MultipartFile>?,
        @Valid @RequestPart("poll") poll: PollDTO?,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ThreadInitComment {
        val threadId = threadService.addThread(thread)
        val commentId = commentController.addComment(
            comment.copy(threadId = threadId),
            files,
            poll,
            emptyList(),
            userDetails,
        )
        threadService.addInitCommentIdToThread(threadId, commentId.commentId)
        return ThreadInitComment(
            threadService.getThreadByThreadId(threadId),
            commentController.getComment(commentId.commentId)
        )
    }
}
