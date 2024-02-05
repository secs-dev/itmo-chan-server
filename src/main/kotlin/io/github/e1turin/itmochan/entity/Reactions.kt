package io.github.e1turin.itmochan.entity

import jakarta.validation.constraints.Size
import org.postgresql.util.PGobject
import org.springframework.data.relational.core.mapping.Table

@Table("Reaction_sets")
data class Reactions(
    val rSetId : Long,
    val reactions : PGobject,
)

data class ReactionDTO(
    val commentId : Long,
    @field:Size(min = 1, max = 6)
    val reaction : String,
)
