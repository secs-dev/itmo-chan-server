package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("Files")
data class File(
    val fileId : UUID,
    val name : String,
    val contentType : String
)

@Table("File_attachments")
data class FileAttachments(
    val commentId : Long,
    val fileId : UUID,
)

