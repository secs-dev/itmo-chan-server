package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.ReactionDTO
import io.github.e1turin.itmochan.entity.Reactions

interface ReactionsService {
    fun addReactionToComment(reaction: ReactionDTO)

    fun getReactions(reactionsId: Long) : Reactions
}