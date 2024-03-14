package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Comment
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface CommentRepository : CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {

    fun findCommentByCommentId(commentId: Long) : Optional<Comment>
    @Query(value="SELECT * FROM \"Comments\" WHERE thread_id = :thread_id ORDER BY creation_date OFFSET :offset LIMIT :limit")
    fun findCommentsByThreadId(
        @Param("thread_id") threadId: Long,
        @Param("offset") offset: Long,
        @Param("limit") limit: Long?): List<Comment>
    @Transactional
    @Query("INSERT INTO \"Comments\"(thread_id, title, content, user_id) VALUES (:thread_id, CAST(:title AS TEXT), CAST(:content AS TEXT), :user_id) RETURNING comment_id")
    fun saveComment(
        @Param("thread_id") threadId : Long,
        @Param("title") title : String? = null,
        @Param("content") content : String,
        @Param("user_id") userId : Long,
    ) : Long

    @Transactional
    @Modifying
    @Query("UPDATE \"Comments\" SET deleted = :deleted WHERE comment_id = :comment_id")
    fun setDeleted(
        @Param("comment_id") commentId: Long,
        @Param("deleted") deleted: Boolean,
    )
}
