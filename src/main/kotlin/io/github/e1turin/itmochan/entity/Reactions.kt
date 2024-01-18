package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Reaction_sets")
data class Reactions(
    val rSetId : Long,
    val reactions : String,
)

data class ReactionDTO(
    val rSetId : Long,
    val reaction : String,
)

