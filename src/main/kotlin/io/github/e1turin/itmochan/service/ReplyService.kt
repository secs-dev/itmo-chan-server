package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.repository.ReplyRepository
import org.springframework.stereotype.Service

@Service
class ReplyService(
   private val replyRepository: ReplyRepository,
) {
    fun saveRepliesTo(
        commentId : Long,
        repliesToIds : List<Long>,
    ) {
        repliesToIds.parallelStream().forEach {
            try {
                replyRepository.saveReply(it, commentId)
            } catch (_ : Exception) {
                //Skip of non-existed replied comment id
            }
        }
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