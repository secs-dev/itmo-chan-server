package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Reactions
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReactionsRepository : CrudRepository<Reactions, Long> {
    fun findReactionsByrSetId(reactionsId: Long) : Optional<Reactions>

    @Modifying
    @Query("CALL append_reaction_to_comment(:reaction, :c_id)")
    fun appendReactionToComment(
        @Param("reaction") reaction: String,
        @Param("c_id") commentId: Long,
        )
}