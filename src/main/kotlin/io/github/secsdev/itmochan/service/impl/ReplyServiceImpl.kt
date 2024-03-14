package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.repository.ReplyRepository
import io.github.secsdev.itmochan.service.ReplyService
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl(
   private val replyRepository: ReplyRepository,
): ReplyService {
    override fun saveRepliesTo(
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

    override fun getCommentsIdsWhomReplied(
        commentId: Long,
    ) : List<Long> {
        return replyRepository.findRepliesByReplyCommentId(commentId).map {it.commentId}
    }

    override fun getRepliesToComment(
        commentId: Long,
    ) : List<Long> {
        return replyRepository.findRepliesByCommentId(commentId).map { it.replyCommentId }
    }
}