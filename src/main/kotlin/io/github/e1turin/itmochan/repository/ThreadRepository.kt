package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Thread
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ThreadRepository : CrudRepository<Thread, Long> {

    fun findAllByTopicId(topicId : Long) : List<Thread>

    fun findThreadByThreadId(threadId : Long) : Optional<Thread>

    @Transactional
    @Query("INSERT INTO \"Threads\"(topic_id) VALUES (:topic_id) RETURNING thread_id")
    fun saveThread(
       @Param("topic_id") topicId: Long,
   ) : Long

    @Transactional
    @Modifying
    @Query("UPDATE \"Threads\" SET init_comment_id = :comment_id WHERE thread_id = :thread_id")
    fun setInitCommentId(
        @Param("thread_id") threadId: Long,
        @Param("comment_id") commentId: Long,
    )
}
