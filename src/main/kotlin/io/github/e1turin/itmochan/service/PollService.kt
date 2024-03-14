package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.PollDTO
import io.github.e1turin.itmochan.response.PollResponse

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