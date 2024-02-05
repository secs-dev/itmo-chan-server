package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.Trash
import io.github.e1turin.itmochan.entity.TrashDTO
import io.github.e1turin.itmochan.service.TrashService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/trash")
class TrashController(
    val trashService : TrashService,
) {
    @PutMapping
    fun throwCommentInTrash(
        @RequestBody trash : TrashDTO,
    ) : ResponseEntity<Void> {
        trashService.throwInTrash(trash)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{commentId}")
    fun getTrash(
        @PathVariable("commentId") commentId : Long,
    ) : Trash {
        return trashService.getTrash(commentId)
    }
}