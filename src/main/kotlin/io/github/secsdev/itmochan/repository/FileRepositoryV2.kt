package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.File
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

interface FileRepositoryV2 : CrudRepository<File, Long> {

    @Transactional
    @Query("INSERT INTO \"Files\"(name, content_type) VALUES (:name, :content_type) RETURNING file_id")
    fun saveFile(
        @Param("name") name : String,
        @Param("content_type") contentType : String,
    ) : UUID

    fun findFileByFileId(fileId: UUID): Optional<File>

    @Transactional
    @Modifying
    @Query("DELETE FROM \"Files\" WHERE file_id = :file_id")
    fun deleteByFileId(
        @Param("file_id") fileId: UUID)
}