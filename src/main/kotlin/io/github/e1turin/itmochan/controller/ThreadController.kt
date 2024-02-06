package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.entity.PollDTO
import io.github.e1turin.itmochan.entity.ThreadDTO
import io.github.e1turin.itmochan.response.ThreadComments
import io.github.e1turin.itmochan.response.ThreadInitComment
import io.github.e1turin.itmochan.service.ThreadService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

const val STANDARD_LIMIT = 5L

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
    ): ThreadComments {
        return threadService.getThreadComments(threadId, offset ?: 0, STANDARD_LIMIT)
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
