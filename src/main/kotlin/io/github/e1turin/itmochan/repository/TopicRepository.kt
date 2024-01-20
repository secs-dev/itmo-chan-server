package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Topic
import org.springframework.data.repository.CrudRepository

interface TopicRepository : CrudRepository<Topic, Long>
