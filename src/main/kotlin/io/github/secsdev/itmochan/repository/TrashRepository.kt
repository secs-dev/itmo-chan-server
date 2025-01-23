package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Trash
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface TrashRepository : CrudRepository<Trash, Long> {
    @Modifying
    @Transactional
    @Query("INSERT INTO Trash (comment_id, reason) VALUES (:commentId, :reason)")
    fun insertIntoTrash(@Param("commentId") commentId: Long, @Param("reason") reason: String?)

    @Modifying
    @Transactional
    @Query("UPDATE Comments SET trashed = true WHERE comment_id = :commentId")
    fun updateCommentAsTrashed(@Param("commentId") commentId: Long)

    fun findTrashByCommentId(commentId: Long) : Optional<Trash>
}
