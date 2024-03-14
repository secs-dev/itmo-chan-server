package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("Trash")
data class Trash(
    val trashId : Long,
    val commentId : Long,
    val reason : String?,
    val recycleDate : LocalDateTime,
)

data class TrashDTO(
    val commentId: Long,
    val reason: String?,
)

