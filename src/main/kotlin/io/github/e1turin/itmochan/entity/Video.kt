package io.github.e1turin.itmochan.entity

import java.io.File

data class Video(
    val videoId : Int,
    val name : String,
    val file : File,
)

data class VideoAttachments(
    val commentId : Int,
    val videoId : Int,
)

