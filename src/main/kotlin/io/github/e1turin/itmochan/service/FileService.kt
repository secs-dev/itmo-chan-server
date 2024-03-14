package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.FilesIds
import org.springframework.web.multipart.MultipartFile

interface FileService {
    companion object AllowedTypes {
        val ALLOWED_PICTURE_TYPES = listOf("image/png", "image/jpeg", "image/gif")
        val ALLOWED_VIDEO_TYPES  = listOf( "video/mpeg", "video/webm", "video/3gpp", "video/mp4")
    }

    fun store(files : List<MultipartFile>?) : FilesIds

    fun linkFileIdAndCommentId(commentId: Long, filesIds : FilesIds)

    fun getFilesIdsAttachedToComment(commentId: Long) : FilesIds
}