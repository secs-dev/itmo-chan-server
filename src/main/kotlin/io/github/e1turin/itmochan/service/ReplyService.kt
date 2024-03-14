package io.github.e1turin.itmochan.service

interface ReplyService {
    fun saveRepliesTo(
        commentId : Long,
        repliesToIds : List<Long>,
    )

    fun getCommentsIdsWhomReplied(
        commentId: Long,
    ) : List<Long>

    fun getRepliesToComment(
        commentId: Long,
    ) : List<Long>
}