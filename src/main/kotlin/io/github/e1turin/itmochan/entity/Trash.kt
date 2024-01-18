package io.github.e1turin.itmochan.entity

import java.time.LocalDateTime

data class Trash(
    val trashId : Long,
    val commentId : Long,
    val reason : String?,
    val recycleDate : LocalDateTime,
)

