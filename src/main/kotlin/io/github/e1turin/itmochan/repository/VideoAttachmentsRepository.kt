package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.VideoAttachments
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface VideoAttachmentsRepository : CrudRepository<VideoAttachments, Long> {

    @Modifying
    @Query("INSERT INTO \"Video_attachments\"(comment_id, video_id) VALUES (:comment_id, :video_id)")
    fun saveVideoAttachment(
        @Param("comment_id") commentId : Long,
        @Param("video_id") videoId: Long,
    )

}