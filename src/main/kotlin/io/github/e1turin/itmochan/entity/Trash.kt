package io.github.e1turin.itmochan.entity

import java.time.LocalDateTime

data class Trash(
    val trashId : Int,
    val commentId : Int,
    val reason : String?,
    val recycleDate : LocalDateTime,
)

