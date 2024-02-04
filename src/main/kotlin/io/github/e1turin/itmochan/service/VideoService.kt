package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.repository.FileRepository
import io.github.e1turin.itmochan.repository.VideoAttachmentsRepository
import io.github.e1turin.itmochan.repository.VideoRepository
import io.github.e1turin.itmochan.response.VideoDTO
import io.github.e1turin.itmochan.security.exception.FileNotFoundException
import io.github.e1turin.itmochan.security.exception.StorageException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.sql.SQLException
import kotlin.math.abs

@Service
class VideoService(
    private val fileRepository: FileRepository,
    private val videoRepository: VideoRepository,
    private val videoAttachmentsRepository: VideoAttachmentsRepository,
) {

    fun store(file: MultipartFile) : Long {
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file")
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
            val videoId = videoRepository.saveVideo(file.originalFilename ?: abs(file.hashCode()).toString(), file.contentType!!, oid)

            fil.delete()
            return videoId

        } catch (e: IOException) {
            e.printStackTrace()
            throw StorageException("Failed to store file")
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to store file")
        }
    }

    fun getVideo(videoId : Long) : VideoDTO {
        val video = videoRepository.findVideoByVideoId(videoId)
        if (video.isEmpty)
            throw FileNotFoundException("File not found")
        try {
            val byteArray = fileRepository.getFileByteArray(video.get().fileOid)
            return VideoDTO(video.get(), byteArray)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to get video")
        }
    }

    fun saveVideoAttachment(commentId: Long, videoId: Long) {
        videoAttachmentsRepository.saveVideoAttachment(commentId, videoId)
    }

    fun deleteVideo(videoId: Long) {
        val video = videoRepository.findVideoByVideoId(videoId)
        if (video.isEmpty)
            throw FileNotFoundException("File not found")

        try {
            fileRepository.deleteFile(video.get().fileOid)
            videoRepository.deleteByVideoId(videoId)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Deletion error") //todo make db exception with 500 code
        }
    }
}
