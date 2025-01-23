package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.repository.VideoAttachmentsRepository
import io.github.secsdev.itmochan.repository.VideoRepository
import io.github.secsdev.itmochan.response.VideoDTO
import io.github.secsdev.itmochan.exception.FileNotFoundException
import io.github.secsdev.itmochan.exception.StorageException
import io.github.secsdev.itmochan.repository.S3Repository
import io.github.secsdev.itmochan.service.VideoService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.sql.SQLException
import java.util.UUID

@Service
@Deprecated("Use FileService for any files")
class VideoServiceImpl(
    private val s3Repository: S3Repository,
    private val videoRepository: VideoRepository,
    private val videoAttachmentsRepository: VideoAttachmentsRepository,
): VideoService {

    override fun store(file: MultipartFile) : UUID {
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file")
        }
        if (file.contentType == null) {
            throw StorageException("Empty content type")
        }
        try {
            val fileContentType = file.contentType!!
            val filename = file.originalFilename ?: "untitled"

            val videoId = videoRepository.saveVideo(filename, fileContentType)
            s3Repository.saveFile(videoId.toString(), fileContentType, file.inputStream)

            return videoId

        } catch (e: IOException) {
            e.printStackTrace()
            throw StorageException("Failed to store file")
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to store file")
        }
    }

    override fun getVideo(videoId : UUID) : VideoDTO {
        val video = videoRepository.findVideoByVideoId(videoId)
        if (video.isEmpty)
            throw FileNotFoundException("File not found")
        try {
            val byteArray = s3Repository.getFileByteArray(video.get().videoId.toString())
            return VideoDTO(video.get(), byteArray)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Failed to get video")
        }
    }

    override fun saveVideoAttachment(commentId: Long, videoId: UUID) {
        videoAttachmentsRepository.saveVideoAttachment(commentId, videoId)
    }

    override fun deleteVideo(videoId: UUID) {
        val video = videoRepository.findVideoByVideoId(videoId)
        if (video.isEmpty)
            throw FileNotFoundException("File not found")

        try {
            s3Repository.deleteFile(video.get().videoId.toString())
            videoRepository.deleteByVideoId(videoId)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw StorageException("Deletion error") //todo make db exception with 500 code
        }
    }

    override fun getVideoIdsByCommentId(commentId: Long) : List<UUID> {
        val videoAttachments = videoAttachmentsRepository.findVideoAttachmentsByCommentId(commentId)
        return videoAttachments.map { it.videoId }
    }
}
