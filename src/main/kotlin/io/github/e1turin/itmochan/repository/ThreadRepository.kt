package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Thread
import org.springframework.data.repository.CrudRepository

interface ThreadRepository : CrudRepository<Thread, Long>
