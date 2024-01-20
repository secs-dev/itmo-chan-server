package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class ThreadService(
    private val service : RoleRepository,
) {
}