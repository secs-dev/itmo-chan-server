package io.github.e1turin.itmochan.entity

data class Reactions(
    val rSetId : Int,
    val reactions : String,
)

data class ReactionDTO(
    val rSetId : Int,
    val reaction : String,
)

