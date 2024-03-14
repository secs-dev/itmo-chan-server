package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Poll
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface PollRepository : CrudRepository<Poll, Long> {

    @Transactional
    @Query("INSERT INTO \"Polls\"(comment_id, title) VALUES (:comment_id, :title) RETURNING poll_id")
    fun savePoll(
        @Param("comment_id") commentId : Long,
        @Param("title") title : String,
    ) : Long

    fun findPollByPollId(pollId : Long) : Optional<Poll>

    fun findPollByCommentId(commentId: Long) : Optional<Poll>
}
