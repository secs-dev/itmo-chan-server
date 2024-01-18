package io.github.e1turin.itmochan.entity

data class Reactions(
    val rSetId : Long,
    val reactions : String,
)

data class ReactionDTO(
    val rSetId : Long,
    val reaction : String,
)

