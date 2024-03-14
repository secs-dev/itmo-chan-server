package io.github.e1turin.itmochan.service.impl

import io.github.e1turin.itmochan.entity.FilesIds
import io.github.e1turin.itmochan.exception.MaxCountFilesException
import io.github.e1turin.itmochan.exception.UnsupportableFileTypeException
import io.github.e1turin.itmochan.service.FileService
import io.github.e1turin.itmochan.service.FileService.AllowedTypes.ALLOWED_PICTURE_TYPES
import io.github.e1turin.itmochan.service.FileService.AllowedTypes.ALLOWED_VIDEO_TYPES
import io.github.e1turin.itmochan.service.PictureService
import io.github.e1turin.itmochan.service.VideoService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileServiceImpl(
    private val pictureService: PictureService,
    private val videoService: VideoService,
): FileService {


    override fun store(files : List<MultipartFile>?) : FilesIds {
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

    override fun linkFileIdAndCommentId(commentId: Long, filesIds : FilesIds) {
        filesIds.picturesIds.parallelStream().forEach {
            pictureService.savePictureAttachment(commentId, it)
        }
        filesIds.videosIds.parallelStream().forEach {
            videoService.saveVideoAttachment(commentId, it)
        }
    }

    override fun getFilesIdsAttachedToComment(commentId: Long) : FilesIds {
        val picturesIds = pictureService.getPictureIdsByCommentId(commentId)
        val videosIds = videoService.getVideoIdsByCommentId(commentId)
        return FilesIds(picturesIds, videosIds)
    }
}