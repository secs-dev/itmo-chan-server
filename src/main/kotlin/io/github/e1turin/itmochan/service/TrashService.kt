package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Trash
import io.github.e1turin.itmochan.entity.TrashDTO
import io.github.e1turin.itmochan.repository.TrashRepository
import io.github.e1turin.itmochan.security.exception.AlreadyTrashedException
import io.github.e1turin.itmochan.security.exception.NoSuchTrashException
import org.springframework.stereotype.Service

@Service
class TrashService(
    private val trashRepository : TrashRepository,
    private val commentService: CommentService,
) {
    fun throwInTrash(trash : TrashDTO) {
        val trashO = trashRepository.findTrashByCommentId(trash.commentId)
        if (trashO.isPresent)
            throw AlreadyTrashedException("This comment has been already trashed")
        trashRepository.throwInTrash(trash.commentId, trash.reason)
    }

    fun getTrash(commentId : Long) : Trash {
        val comment = commentService.getComment(commentId)
        val trash = trashRepository.findTrashByCommentId(commentId)
        if (trash.isEmpty)
            throw NoSuchTrashException("No such trash was found")
        return trash.get()
    }
}