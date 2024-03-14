package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Comment
import io.github.e1turin.itmochan.entity.CommentDTO
import io.github.e1turin.itmochan.response.CommentResponse

interface CommentService {
    fun addComment(commentDTO: CommentDTO, username: String): Long

    fun getComment(commentId: Long): Comment
    fun getCommentResponse(commentId: Long): CommentResponse

    fun getCommentsByThreadId(threadId: Long, offset: Long, limit: Long?): List<Comment>

    fun deleteComment(commentId: Long, username: String)
}