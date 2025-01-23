package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.repository.PictureAttachmentsRepository
import io.github.secsdev.itmochan.repository.PictureRepository
import io.github.secsdev.itmochan.response.PictureDTO
import io.github.secsdev.itmochan.exception.FileNotFoundException
import io.github.secsdev.itmochan.exception.StorageException
import io.github.secsdev.itmochan.repository.S3Repository
import io.github.secsdev.itmochan.service.PictureService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.sql.SQLException
import java.util.UUID

@Service
@Deprecated("Use FileService for any files")
class PictureServiceImpl(
    private val s3Repository: S3Repository,
    private val pictureRepository: PictureRepository,
    private val pictureAttachmentsRepository: PictureAttachmentsRepository,
): PictureService {

    override fun store(file: MultipartFile) : UUID {
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file") //todo 400
        }
        if (file.contentType == null) {
            throw StorageException("Empty content type")
        }
        try {
            val fileContentType = file.contentType!!
            val filename = file.originalFilename ?: "untitled"

            val pictureId = pictureRepository.savePicture(filename, fileContentType)
            s3Repository.saveFile(pictureId.toString(), fileContentType, file.inputStream)

            return pictureId

        } catch (e: IOException) {
            e.printStackTrace()
            throw StorageException("Failed to store file") //todo 500
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to store file") //todo 500
        }
    }

    override fun getPicture(pictureId : UUID) : PictureDTO {
        val picture = pictureRepository.findPictureByPictureId(pictureId)
        if (picture.isEmpty)
            throw FileNotFoundException("File not found")
        try {
            val byteArray = s3Repository.getFileByteArray(picture.get().pictureId.toString())
            return PictureDTO(picture.get(), byteArray)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to get picture") //todo 500
        }
    }

    override fun savePictureAttachment(commentId: Long, pictureId: UUID) {
        pictureAttachmentsRepository.savePictureAttachment(commentId, pictureId)
    }

    override fun deletePicture(pictureId: UUID) {
        val picture = pictureRepository.findPictureByPictureId(pictureId)
        if (picture.isEmpty)
            throw FileNotFoundException("File not found")

        try {
            s3Repository.deleteFile(picture.get().pictureId.toString())
            pictureRepository.deleteByPictureId(pictureId)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Deletion error") //todo make db exception with 500 code
        }
    }

    override fun getPictureIdsByCommentId(commentId: Long) : List<UUID> {
        val pictureAttachments = pictureAttachmentsRepository.findPictureAttachmentsByCommentId(commentId)
        return pictureAttachments.map { it.pictureId }
    }
}
