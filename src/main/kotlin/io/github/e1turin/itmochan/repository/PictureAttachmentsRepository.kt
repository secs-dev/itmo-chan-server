package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.PictureAttachments
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface PictureAttachmentsRepository : CrudRepository<PictureAttachments, Long> {

    @Modifying
    @Query("INSERT INTO \"Picture_attachments\"(comment_id, picture_id) VALUES (:comment_id, :picture_id)")
    fun savePictureAttachment(
        @Param("comment_id") commentId : Long,
        @Param("picture_id") pictureId: Long,
    )

    fun findPictureAttachmentsByCommentId(commentId: Long) : List<PictureAttachments>

}