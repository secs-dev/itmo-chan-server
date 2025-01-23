package io.github.secsdev.itmochan.controller

import io.github.secsdev.itmochan.service.FileService
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/media")
class MediaController(
    private val fileService: FileService,
) {
    @GetMapping("{file_id}")
    @ResponseBody fun getFile(
        @PathVariable("file_id") fileId: UUID,
    ) : ResponseEntity<ByteArray> {
        val file = fileService.getFile(fileId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.valueOf(file.file.contentType)
        headers.contentDisposition =
            ContentDisposition.builder("inline").filename(file.file.name).build()

        return ResponseEntity.ok()
            .headers(headers)
            .body(file.byteArray)
    }

    @DeleteMapping("{file_id}")
    fun deleteFile(
        @PathVariable("file_id") fileId: UUID,
    ) {
        fileService.deleteFile(fileId)
    }
}