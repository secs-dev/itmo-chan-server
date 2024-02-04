package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.response.PollResponse
import io.github.e1turin.itmochan.service.PollService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/poll")
class PollController(
    private val pollService: PollService
) {

    @GetMapping("{pollId}")
    fun getPoll(
        @PathVariable("pollId") pollId: Long,
    ): PollResponse {
       return pollService.getPoll(pollId)
    }

    @PostMapping("{pollId}")
    fun voteInPoll(
        @PathVariable("pollId") pollId: Long,
        @RequestBody answersIds: List<Long>,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        pollService.voteInPoll(pollId, answersIds, userDetails.username)
    }
}