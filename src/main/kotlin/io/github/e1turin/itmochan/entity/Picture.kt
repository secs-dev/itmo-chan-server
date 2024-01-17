package io.github.e1turin.itmochan.entity

import java.io.File

data class Picture(
    val pictureId : Int,
    val name : String,
    val file : File,
)

data class PictureAttachments(
    val commentId : Int,
    val pictureId : Int,
)
