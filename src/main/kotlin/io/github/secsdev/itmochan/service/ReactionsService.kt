package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.ReactionDTO
import io.github.secsdev.itmochan.entity.Reactions

interface ReactionsService {
    fun addReactionToComment(reaction: ReactionDTO)

    fun getReactions(reactionsId: Long) : Reactions
}