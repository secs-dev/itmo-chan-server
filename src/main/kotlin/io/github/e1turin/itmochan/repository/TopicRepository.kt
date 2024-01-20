package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Role
import io.github.e1turin.itmochan.entity.Topic
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface TopicRepository : CrudRepository<Topic, Long> {

    fun findTopicByTopicId(id: Long) : Optional<Topic>

    @Modifying
    @Query("INSERT INTO \"Topics\"(name, description) VALUES (:name, :description)")
    fun saveTopic(
        @Param("name") name: String,
        @Param("description") description : String,
    )
}
