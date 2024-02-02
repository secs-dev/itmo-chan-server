package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.ThreadCommentDTO
import io.github.e1turin.itmochan.response.ThreadComments
import io.github.e1turin.itmochan.response.ThreadInitComment
import io.github.e1turin.itmochan.service.ThreadService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

const val STANDARD_LIMIT = 5L

@RestController
@RequestMapping("/api/thread")
class ThreadController(
    private val threadService: ThreadService
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

    @PutMapping
    fun addThread(
        @RequestBody thread: ThreadCommentDTO,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ThreadInitComment {
        return threadService.addThread(thread, userDetails.username)
    }
}
