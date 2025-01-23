package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.File
import io.github.secsdev.itmochan.exception.FileNotFoundException
import io.github.secsdev.itmochan.exception.StorageException
import io.github.secsdev.itmochan.repository.*
import io.github.secsdev.itmochan.response.FileDTO
import io.github.secsdev.itmochan.service.FileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.sql.SQLException
import java.util.UUID

@Service
class FileServiceImpl(
    private val s3Repository: S3Repository,
    private val fileRepository: FileRepositoryV2,
    private val fileAttachmentsRepository: FileAttachmentsRepository,
): FileService {

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

            val fileId = fileRepository.saveFile(filename, fileContentType)
            s3Repository.saveFile(fileId.toString(), fileContentType, file.inputStream)

            return fileId

        } catch (e: IOException) {
            e.printStackTrace()
            throw StorageException("Failed to store file") //todo 500
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to store file") //todo 500
        }
    }

    override fun getFile(fileId : UUID) : FileDTO {
        val file = fileRepository.findFileByFileId(fileId)
        if (file.isEmpty)
            throw FileNotFoundException("File not found")
        try {
            val byteArray = s3Repository.getFileByteArray(file.get().fileId.toString())
            return FileDTO(file.get(), byteArray)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to get file") //todo 500
        }
    }

    override fun getFileMeta(fileId: UUID): File {
        val file = fileRepository.findFileByFileId(fileId)
        if (file.isEmpty)
            throw FileNotFoundException("File not found")
        return file.get()
    }

    override fun saveFileAttachment(commentId: Long, fileId: UUID) {
        fileAttachmentsRepository.saveFileAttachment(commentId, fileId)
    }

    override fun deleteFile(fileId: UUID) {
        val file = fileRepository.findFileByFileId(fileId)
        if (file.isEmpty)
            throw FileNotFoundException("File not found")

        try {
            s3Repository.deleteFile(file.get().fileId.toString())
            fileRepository.deleteByFileId(fileId)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Deletion error") //todo make db exception with 500 code
        }
    }

    override fun getFileIdsByCommentId(commentId: Long) : List<UUID> {
        val fileAttachments = fileAttachmentsRepository.findFileAttachmentsByCommentId(commentId)
        return fileAttachments.map { it.fileId }
    }
}
