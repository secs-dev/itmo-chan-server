package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Trash
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface TrashRepository : CrudRepository<Trash, Long> {
    @Modifying
    @Query("CALL throw_in_trash(:comment_id, :reason)")
    fun throwInTrash(
        @Param("comment_id") commentId : Long,
        @Param("reason") reason : String?,
    )

    fun findTrashByCommentId(commentId: Long) : Optional<Trash>
}
