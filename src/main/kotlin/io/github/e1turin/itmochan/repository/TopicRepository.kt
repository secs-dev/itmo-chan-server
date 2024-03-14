package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Topic
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface TopicRepository : CrudRepository<Topic, Long> {

    fun findTopicByTopicId(id: Long) : Optional<Topic>

    @Transactional
    @Modifying
    @Query("INSERT INTO \"Topics\"(name, description) VALUES (:name, :description)")
    fun saveTopic(
        @Param("name") name: String,
        @Param("description") description : String,
    )
}
