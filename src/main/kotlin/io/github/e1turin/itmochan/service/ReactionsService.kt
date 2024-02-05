package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.ReactionDTO
import io.github.e1turin.itmochan.entity.Reactions
import io.github.e1turin.itmochan.repository.ReactionsRepository
import io.github.e1turin.itmochan.exception.NoSuchReactionsException
import org.springframework.stereotype.Service

@Service
class ReactionsService(
    private val commentService: CommentService,
    private val reactionsRepository : ReactionsRepository,
) {
    fun addReactionToComment(reaction: ReactionDTO) {
        val comment = commentService.getComment(reaction.commentId)
        reactionsRepository.appendReactionToComment(reaction.reaction, reaction.commentId) //TODO catch script exception
    }
    fun getReactions(reactionsId: Long) : Reactions {
        val reactions = reactionsRepository.findReactionsByrSetId(reactionsId)
        if (reactions.isEmpty)
            throw NoSuchReactionsException("No such reactions was found")
        return reactions.get()
    }
}