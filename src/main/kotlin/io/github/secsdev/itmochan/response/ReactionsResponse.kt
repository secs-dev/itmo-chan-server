package io.github.secsdev.itmochan.response

data class ReactionsResponse(
    val rSetId : Long,
    val reactions : Map<*, *>,
)
