package io.github.secsdev.itmochan.controller

import io.github.secsdev.itmochan.entity.ReactionDTO
import io.github.secsdev.itmochan.response.ReactionsResponse
import io.github.secsdev.itmochan.service.ReactionsService
import io.github.secsdev.itmochan.utils.convertJsonToMap
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reactions")
class ReactionsController(
    private val reactionsService: ReactionsService,
) {

    @PostMapping
    fun addReactionToComment(
        @Valid @RequestBody reaction: ReactionDTO,
    ) {
        reactionsService.addReactionToComment(reaction)
    }

    @GetMapping("{reactionsId}")
    fun getReactions(
        @PathVariable("reactionsId") reactionsId : Long
    ): ReactionsResponse {
        val reactions = reactionsService.getReactions(reactionsId)
        val map = convertJsonToMap(reactions.reactions.value ?: "{}")
        return ReactionsResponse(reactions.rSetId, map)
    }
}
