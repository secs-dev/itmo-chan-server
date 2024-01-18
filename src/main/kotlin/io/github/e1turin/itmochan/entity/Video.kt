package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table
import java.io.File

@Table("Videos")
data class Video(
    val videoId : Long,
    val name : String,
    val file : File,
)

@Table("Video_attachments")
data class VideoAttachments(
    val commentId : Long,
    val videoId : Long,
)

