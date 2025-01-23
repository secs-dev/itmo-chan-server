package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.response.PictureDTO
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface PictureService {
    fun store(file: MultipartFile) : UUID

    fun getPicture(pictureId : UUID) : PictureDTO

    fun savePictureAttachment(commentId: Long, pictureId: UUID)

    fun deletePicture(pictureId: UUID)

    fun getPictureIdsByCommentId(commentId: Long) : List<UUID>
}