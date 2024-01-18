package io.github.e1turin.itmochan.entity

import java.io.File

data class Video(
    val videoId : Long,
    val name : String,
    val file : File,
)

data class VideoAttachments(
    val commentId : Long,
    val videoId : Long,
)

