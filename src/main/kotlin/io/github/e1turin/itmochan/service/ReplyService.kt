package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.repository.ReplyRepository
import org.springframework.stereotype.Service

@Service
class ReplyService(
   private val replyRepository: ReplyRepository,
) {
    fun saveReplies(
        commentId : Long,
        repliesIds : List<Long>,
    ) {
        repliesIds.parallelStream().forEach { replyRepository.saveReply(commentId, it) }
    }

    fun getCommentsIdsWhomReplied(
        commentId: Long,
    ) : List<Long> {
        return replyRepository.findRepliesByReplyCommentId(commentId).map {it.commentId}
    }

    fun getRepliesToComment(
        commentId: Long,
    ) : List<Long> {
        return replyRepository.findRepliesByCommentId(commentId).map { it.replyCommentId }
    }
}