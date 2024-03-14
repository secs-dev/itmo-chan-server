package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.Trash
import io.github.secsdev.itmochan.entity.TrashDTO

interface TrashService {
    fun throwInTrash(trash : TrashDTO)

    fun getTrash(commentId : Long) : Trash
}