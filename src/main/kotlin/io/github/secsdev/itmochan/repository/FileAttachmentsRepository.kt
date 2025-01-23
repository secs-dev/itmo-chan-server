package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.FileAttachments
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface FileAttachmentsRepository : CrudRepository<FileAttachments, Long> {

    @Modifying
    @Query("INSERT INTO \"File_attachments\"(comment_id, file_id) VALUES (:comment_id, :file_id)")
    fun saveFileAttachment(
        @Param("comment_id") commentId : Long,
        @Param("file_id") fileId: UUID,
    )

    fun findFileAttachmentsByCommentId(commentId: Long) : List<FileAttachments>

}