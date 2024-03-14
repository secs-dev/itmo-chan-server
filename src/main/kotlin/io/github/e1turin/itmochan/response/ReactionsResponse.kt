package io.github.e1turin.itmochan.response

data class ReactionsResponse(
    val rSetId : Long,
    val reactions : Map<*, *>,
)
