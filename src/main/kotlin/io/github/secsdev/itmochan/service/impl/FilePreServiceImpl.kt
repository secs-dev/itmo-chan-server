package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.FilesIds
import io.github.secsdev.itmochan.exception.MaxCountFilesException
import io.github.secsdev.itmochan.exception.UnsupportableFileTypeException
import io.github.secsdev.itmochan.service.FilePreService
import io.github.secsdev.itmochan.service.FilePreService.AllowedTypes.ALLOWED_PICTURE_TYPES
import io.github.secsdev.itmochan.service.FilePreService.AllowedTypes.ALLOWED_VIDEO_TYPES
import io.github.secsdev.itmochan.service.FileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FilePreServiceImpl(
    private val fileService: FileService,
): FilePreService {


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
            files.parallelStream().filter { it.contentType in ALLOWED_PICTURE_TYPES }.map{fileService.store(it)}.toList()
        val videoIds =
            files.parallelStream().filter { it.contentType in ALLOWED_VIDEO_TYPES }.map { fileService.store(it) }.toList()
        return FilesIds(pictureIds, videoIds)
    }

    override fun linkFileIdAndCommentId(commentId: Long, filesIds : FilesIds) {
        filesIds.picturesIds.parallelStream().forEach {
            fileService.saveFileAttachment(commentId, it)
        }
        filesIds.videosIds.parallelStream().forEach {
            fileService.saveFileAttachment(commentId, it)
        }
    }

    override fun getFilesIdsAttachedToComment(commentId: Long) : FilesIds {
        val fileIds = fileService.getFileIdsByCommentId(commentId)
        val picturesIds = fileIds.filter { fileService.getFileMeta(it).contentType in ALLOWED_PICTURE_TYPES }
        val videosIds = fileIds.filter { fileService.getFileMeta(it).contentType in ALLOWED_VIDEO_TYPES }
        return FilesIds(picturesIds, videosIds)
    }
}
