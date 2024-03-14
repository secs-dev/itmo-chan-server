package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.Trash
import io.github.secsdev.itmochan.entity.TrashDTO
import io.github.secsdev.itmochan.repository.TrashRepository
import io.github.secsdev.itmochan.exception.AlreadyTrashedException
import io.github.secsdev.itmochan.exception.NoSuchTrashException
import io.github.secsdev.itmochan.service.CommentService
import io.github.secsdev.itmochan.service.TrashService
import org.springframework.stereotype.Service

@Service
class TrashServiceImpl(
    private val trashRepository : TrashRepository,
    private val commentService: CommentService,
): TrashService {
    override fun throwInTrash(trash : TrashDTO) {
        val trashO = trashRepository.findTrashByCommentId(trash.commentId)
        if (trashO.isPresent)
            throw AlreadyTrashedException("This comment has been already trashed")
        trashRepository.throwInTrash(trash.commentId, trash.reason)
    }

    override fun getTrash(commentId : Long) : Trash {
        val comment = commentService.getComment(commentId)
        val trash = trashRepository.findTrashByCommentId(commentId)
        if (trash.isEmpty)
            throw NoSuchTrashException("No such trash was found")
        return trash.get()
    }
}