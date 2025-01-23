package io.github.secsdev.itmochan.response

import io.github.secsdev.itmochan.entity.File
import io.github.secsdev.itmochan.entity.Picture
import io.github.secsdev.itmochan.entity.Video

class PictureDTO(
    val picture : Picture,
    val byteArray: ByteArray,
)

class VideoDTO(
    val video : Video,
    val byteArray: ByteArray,
)

class FileDTO(
    val file: File,
    val byteArray: ByteArray,
)
