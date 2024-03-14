package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Role

interface RolesService {
    fun findRoleByName(name: String): Role
}