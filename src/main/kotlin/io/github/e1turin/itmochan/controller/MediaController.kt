package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.ThreadDTO
import io.github.e1turin.itmochan.service.PictureService
import io.github.e1turin.itmochan.service.VideoService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/media")
class MediaController(
    private val pictureService : PictureService,
    private val videoService : VideoService,
) {
    @PostMapping
    fun handleFileUpload(
        @RequestParam("file") files: List<MultipartFile>,
    ) {
        files.stream().forEach { f -> pictureService.store(f)}
    }
}