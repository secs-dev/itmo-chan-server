package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Long>
