package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Video
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface VideoRepository : CrudRepository<Video, Long> {

    @Transactional
    @Query("INSERT INTO \"Videos\"(name, content_type, file_oid) VALUES (:name, :content_type, CAST(:oid AS OID)) RETURNING video_id")
    fun saveVideo(
        @Param("name") name : String,
        @Param("content_type") contentType : String,
        @Param("oid") oid : Long,
    ) : Long

    fun findVideoByVideoId(pictureId: Long): Optional<Video>

    @Transactional
    @Modifying
    @Query("DELETE FROM \"Videos\" WHERE video_id = :video_id")
    fun deleteByVideoId(
        @Param("video_id") videoId: Long)
}