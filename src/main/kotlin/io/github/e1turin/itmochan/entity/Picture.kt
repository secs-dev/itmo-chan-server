package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Pictures")
data class Picture(
    val pictureId : Long,
    val name : String,
    val contentType : String,
    val fileOid : Long,
)

@Table("Picture_attachments")
data class PictureAttachments(
    val commentId : Long,
    val pictureId : Long,
)
