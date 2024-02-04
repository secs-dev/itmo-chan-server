package io.github.e1turin.itmochan.response

import io.github.e1turin.itmochan.entity.Picture
import io.github.e1turin.itmochan.entity.Video

class PictureDTO(
    val picture : Picture,
    val byteArray: ByteArray,
)

class VideoDTO(
    val video : Video,
    val byteArray: ByteArray,
)