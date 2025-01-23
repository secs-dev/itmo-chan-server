package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.PictureAttachments
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface PictureAttachmentsRepository : CrudRepository<PictureAttachments, Long> {

    @Modifying
    @Query("INSERT INTO \"Picture_attachments\"(comment_id, picture_id) VALUES (:comment_id, :picture_id)")
    fun savePictureAttachment(
        @Param("comment_id") commentId : Long,
        @Param("picture_id") pictureId: UUID,
    )

    fun findPictureAttachmentsByCommentId(commentId: Long) : List<PictureAttachments>

}