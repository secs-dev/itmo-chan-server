package io.github.secsdev.itmochan.entity

import org.hibernate.annotations.Type
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("Pictures")
data class Picture(
    val pictureId : UUID,
    val name : String,
    val contentType : String,
)

@Table("Picture_attachments")
data class PictureAttachments(
    val commentId : Long,
    val pictureId : UUID,
)
