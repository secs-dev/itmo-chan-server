package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.PollAnswer
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface PollAnswerRepository : CrudRepository<PollAnswer, Long> {

    @Transactional
    @Modifying
    @Query("INSERT INTO \"Poll_answers\"(poll_id, answer_title) VALUES (:poll_id, :answer_title)")
    fun savePollAnswer(
        @Param("poll_id") pollId: Long,
        @Param("answer_title") answerTitle : String,
    )

    @Modifying
    @Transactional
    @Query("CALL vote_in_poll(:user_id, :poll_id, array[:answers])")
    fun voteInPoll(
        @Param("user_id") userId: Long,
        @Param("poll_id") pollId: Long,
        @Param("answers") answersIds: List<Long>,
    )

    fun findPollAnswersByPollId(pollId: Long) : List<PollAnswer>
}
