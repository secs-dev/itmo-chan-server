package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.File
import io.github.secsdev.itmochan.response.FileDTO
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface FileService {
    fun store(file: MultipartFile) : UUID

    fun getFile(fileId : UUID) : FileDTO

    fun getFileMeta(fileId : UUID) : File
    fun saveFileAttachment(commentId: Long, fileId: UUID)

    fun deleteFile(fileId: UUID)

    fun getFileIdsByCommentId(commentId: Long) : List<UUID>
}
