package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.response.PictureDTO
import org.springframework.web.multipart.MultipartFile

interface PictureService {
    fun store(file: MultipartFile) : Long

    fun getPicture(pictureId : Long) : PictureDTO

    fun savePictureAttachment(commentId: Long, pictureId: Long)

    fun deletePicture(pictureId: Long)

    fun getPictureIdsByCommentId(commentId: Long) : List<Long>
}