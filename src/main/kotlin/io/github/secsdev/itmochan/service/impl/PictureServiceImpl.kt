package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.repository.FileRepository
import io.github.secsdev.itmochan.repository.PictureAttachmentsRepository
import io.github.secsdev.itmochan.repository.PictureRepository
import io.github.secsdev.itmochan.response.PictureDTO
import io.github.secsdev.itmochan.exception.FileNotFoundException
import io.github.secsdev.itmochan.exception.StorageException
import io.github.secsdev.itmochan.service.PictureService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.sql.SQLException
import kotlin.math.abs

@Service
class PictureServiceImpl(
    private val fileRepository: FileRepository,
    private val pictureRepository: PictureRepository,
    private val pictureAttachmentsRepository: PictureAttachmentsRepository,
): PictureService {

    override fun store(file: MultipartFile) : Long {
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file") //todo 400
        }
        if (file.contentType == null) {
            throw StorageException("Empty content type")
        }
        try {
            val fil = File.createTempFile("tempfile--", null)

            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, fil.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                )
            }

            val oid = fileRepository.saveFile(fil)
            val pictureId = pictureRepository.savePicture(file.originalFilename ?: abs(file.hashCode()).toString(), file.contentType!!, oid)

            fil.delete()
            return pictureId

        } catch (e: IOException) {
            e.printStackTrace()
            throw StorageException("Failed to store file") //todo 500
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to store file") //todo 500
        }
    }

    override fun getPicture(pictureId : Long) : PictureDTO {
        val picture = pictureRepository.findPictureByPictureId(pictureId)
        if (picture.isEmpty)
            throw FileNotFoundException("File not found")
        try {
            val byteArray = fileRepository.getFileByteArray(picture.get().fileOid)
            return PictureDTO(picture.get(), byteArray)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to get picture") //todo 500
        }
    }

    override fun savePictureAttachment(commentId: Long, pictureId: Long) {
        pictureAttachmentsRepository.savePictureAttachment(commentId, pictureId)
    }

    override fun deletePicture(pictureId: Long) {
        val picture = pictureRepository.findPictureByPictureId(pictureId)
        if (picture.isEmpty)
            throw FileNotFoundException("File not found")

        try {
            fileRepository.deleteFile(picture.get().fileOid)
            pictureRepository.deleteByPictureId(pictureId)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Deletion error") //todo make db exception with 500 code
        }
    }

    override fun getPictureIdsByCommentId(commentId: Long) : List<Long> {
        val pictureAttachments = pictureAttachmentsRepository.findPictureAttachmentsByCommentId(commentId)
        return pictureAttachments.map { it.pictureId }
    }
}
