package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Videos")
data class Video(
    val videoId : Long,
    val name : String,
    val contentType : String,
    val fileOid : Long,
)

@Table("Video_attachments")
data class VideoAttachments(
    val commentId : Long,
    val videoId : Long,
)

