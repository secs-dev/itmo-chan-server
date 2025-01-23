package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.Trash
import io.github.secsdev.itmochan.entity.TrashDTO
import io.github.secsdev.itmochan.exception.AlreadyTrashedException
import io.github.secsdev.itmochan.exception.NoSuchTrashException
import io.github.secsdev.itmochan.repository.TrashRepository
import io.github.secsdev.itmochan.service.CommentService
import io.github.secsdev.itmochan.service.TrashService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TrashServiceImpl(
    private val trashRepository : TrashRepository,
    private val commentService: CommentService,
): TrashService {
    override fun throwInTrash(trash : TrashDTO) {
        val trashO = trashRepository.findTrashByCommentId(trash.commentId)
        if (trashO.isPresent)
            throw AlreadyTrashedException("This comment has been already trashed")
        performTrashOperation(trash = trash)
    }

    override fun getTrash(commentId : Long) : Trash {
        val comment = commentService.getComment(commentId)
        val trash = trashRepository.findTrashByCommentId(commentId)
        if (trash.isEmpty)
            throw NoSuchTrashException("No such trash was found")
        return trash.get()
    }

    @Transactional
    private fun performTrashOperation(trash: TrashDTO) {
        trashRepository.insertIntoTrash(commentId = trash.commentId, reason = trash.reason)
        trashRepository.updateCommentAsTrashed(commentId = trash.commentId)
    }
}