package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.PollAnswer
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
    @Query("UPDATE PollAnswer pa SET pa.votesNumber = pa.votesNumber + 1 WHERE pa.pollAnswerId IN :answers AND pa.pollId = :pollId")
    fun incrementVotes(@Param("pollId") pollId: Long, @Param("answers") answers: List<Long>)

    @Modifying
    @Transactional
    @Query("INSERT INTO VotedUsers (poll_id, user_id) VALUES (:pollId, :userId)")
    fun insertVotedUser(@Param("pollId") pollId: Long, @Param("userId") userId: Long)

    fun findPollAnswersByPollId(pollId: Long) : List<PollAnswer>
}
