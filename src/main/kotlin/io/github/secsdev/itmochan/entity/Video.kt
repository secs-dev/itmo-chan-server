package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("Videos")
data class Video(
    val videoId : UUID,
    val name : String,
    val contentType : String
)

@Table("Video_attachments")
data class VideoAttachments(
    val commentId : Long,
    val videoId : UUID,
)

