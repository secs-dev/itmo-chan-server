package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.Role

interface RolesService {
    fun findRoleByName(name: String): Role
}