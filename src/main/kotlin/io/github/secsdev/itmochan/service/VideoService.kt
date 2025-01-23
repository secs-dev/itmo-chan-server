package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.response.VideoDTO
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Deprecated("Use FileService for any files")
interface VideoService {
    fun store(file: MultipartFile) : UUID

    fun getVideo(videoId: UUID): VideoDTO

    fun saveVideoAttachment(commentId: Long, videoId: UUID)

    fun deleteVideo(videoId: UUID)

    fun getVideoIdsByCommentId(commentId: Long) : List<UUID>
}