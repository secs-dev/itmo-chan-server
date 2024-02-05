package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Reply
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ReplyRepository : CrudRepository<Reply, Long> {
    @Modifying
    @Query("INSERT INTO \"Replies\"(comment_id, reply_comment_id) VALUES (:comment_id, :reply_comment_id)")
    fun saveReply(
        @Param("comment_id") commentId : Long,
        @Param("reply_comment_id") replyCommentId : Long,
    )

    fun findRepliesByCommentId(
        commentId: Long,
    ) : List<Reply>

    fun findRepliesByReplyCommentId(
        replyCommentId: Long,
    ) : List<Reply>
}
