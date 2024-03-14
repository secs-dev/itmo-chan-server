package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.Comment
import io.github.secsdev.itmochan.entity.CommentDTO
import io.github.secsdev.itmochan.response.CommentResponse

interface CommentService {
    fun addComment(commentDTO: CommentDTO, username: String): Long

    fun getComment(commentId: Long): Comment
    fun getCommentResponse(commentId: Long): CommentResponse

    fun getCommentsByThreadId(threadId: Long, offset: Long, limit: Long?): List<Comment>

    fun deleteComment(commentId: Long, username: String)
}