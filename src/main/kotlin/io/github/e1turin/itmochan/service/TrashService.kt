package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Trash
import io.github.e1turin.itmochan.entity.TrashDTO

interface TrashService {
    fun throwInTrash(trash : TrashDTO)

    fun getTrash(commentId : Long) : Trash
}