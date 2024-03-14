package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.PollDTO
import io.github.secsdev.itmochan.response.PollResponse

interface PollService {
    fun createPoll(
        commentId: Long,
        poll: PollDTO?,
    )

    fun voteInPoll(
        pollId: Long,
        answersIds: List<Long>,
        username: String,
    )

    fun getPoll(pollId: Long): PollResponse

    fun getPollIdByCommentId(commentId: Long): Long
}