package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.ReactionDTO
import io.github.secsdev.itmochan.entity.Reactions
import io.github.secsdev.itmochan.repository.ReactionsRepository
import io.github.secsdev.itmochan.exception.NoSuchReactionsException
import io.github.secsdev.itmochan.service.CommentService
import io.github.secsdev.itmochan.service.ReactionsService
import org.springframework.stereotype.Service

@Service
class ReactionsServiceImpl(
    private val commentService: CommentService,
    private val reactionsRepository : ReactionsRepository,
): ReactionsService {
    override fun addReactionToComment(reaction: ReactionDTO) {
        val comment = commentService.getComment(reaction.commentId)
        reactionsRepository.appendReactionToComment(reaction.reaction, reaction.commentId) //TODO catch script exception
    }
    override fun getReactions(reactionsId: Long) : Reactions {
        val reactions = reactionsRepository.findReactionsByrSetId(reactionsId)
        if (reactions.isEmpty)
            throw NoSuchReactionsException("No such reactions was found")
        return reactions.get()
    }
}