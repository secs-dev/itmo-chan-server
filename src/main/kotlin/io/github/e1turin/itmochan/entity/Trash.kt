package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("Trash")
data class Trash(
    val trashId : Long,
    val commentId : Long,
    val reason : String?,
    val recycleDate : LocalDateTime,
)

