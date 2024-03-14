package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Picture
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface PictureRepository : CrudRepository<Picture, Long> {

    @Transactional
    @Query("INSERT INTO \"Pictures\"(name, content_type, file_oid) VALUES (:name, :content_type, CAST(:oid AS OID)) RETURNING picture_id")
    fun savePicture(
        @Param("name") name : String,
        @Param("content_type") contentType : String,
        @Param("oid") oid : Long,
    ) : Long

    fun findPictureByPictureId(pictureId: Long): Optional<Picture>

    @Transactional
    @Modifying
    @Query("DELETE FROM \"Pictures\" WHERE picture_id = :picture_id")
    fun deleteByPictureId(
        @Param("picture_id") pictureId: Long)
}