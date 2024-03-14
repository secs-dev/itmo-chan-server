package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.response.VideoDTO
import org.springframework.web.multipart.MultipartFile

interface VideoService {
    fun store(file: MultipartFile) : Long

    fun getVideo(videoId: Long): VideoDTO

    fun saveVideoAttachment(commentId: Long, videoId: Long)

    fun deleteVideo(videoId: Long)

    fun getVideoIdsByCommentId(commentId: Long) : List<Long>
}