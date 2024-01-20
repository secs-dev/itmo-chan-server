package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val service : CommentRepository,
) {
}