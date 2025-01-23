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
    @Query("INSERT INTO \"Videos\"(name, content_type) VALUES (:name, :content_type) RETURNING video_id")
    fun saveVideo(
        @Param("name") name : String,
        @Param("content_type") contentType : String,
    ) : UUID

    fun findVideoByVideoId(pictureId: UUID): Optional<Video>

    @Transactional
    @Modifying
    @Query("DELETE FROM \"Videos\" WHERE video_id = :video_id")
    fun deleteByVideoId(
        @Param("video_id") videoId: UUID)
}