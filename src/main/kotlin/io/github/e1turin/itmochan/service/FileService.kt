package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.FilesIds
import io.github.e1turin.itmochan.security.exception.MaxCountFilesException
import io.github.e1turin.itmochan.security.exception.UnsupportableFileTypeException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val pictureService: PictureService,
    private val videoService: VideoService,
) {
    companion object AllowedTypes {
        val ALLOWED_PICTURE_TYPES = listOf("image/png", "image/jpeg", "image/gif")
        val ALLOWED_VIDEO_TYPES  = listOf( "video/mpeg", "video/webm", "video/3gpp", "video/mp4")
    }

    fun store(files : List<MultipartFile>?) : FilesIds {
        if (files == null)
            return FilesIds(emptyList(), emptyList())
        if (files.size > 10) {
            throw MaxCountFilesException("You can upload only 10 files")
        }
        val fileWithUnsupportableType = files.stream().filter {
            it.contentType !in ALLOWED_PICTURE_TYPES && it.contentType !in ALLOWED_VIDEO_TYPES
        }.findAny()
        if (fileWithUnsupportableType.isPresent) {
            throw UnsupportableFileTypeException(
                "You uploaded file with restricted type: ${fileWithUnsupportableType.get().contentType}")
        }
        val pictureIds =
            files.parallelStream().filter { it.contentType in ALLOWED_PICTURE_TYPES }.map{pictureService.store(it)}.toList()
        val videoIds =
            files.parallelStream().filter { it.contentType in ALLOWED_VIDEO_TYPES }.map { videoService.store(it) }.toList()
        return FilesIds(pictureIds, videoIds)
    }

    fun linkFileIdAndCommentId(commentId: Long, filesIds : FilesIds) {
        filesIds.picturesIds.parallelStream().forEach {
            pictureService.savePictureAttachment(commentId, it)
        }
        filesIds.videosIds.parallelStream().forEach {
            videoService.saveVideoAttachment(commentId, it)
        }
    }
}