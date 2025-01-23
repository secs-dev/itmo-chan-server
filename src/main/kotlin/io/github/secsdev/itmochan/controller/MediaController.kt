package io.github.secsdev.itmochan.controller

import io.github.secsdev.itmochan.service.PictureService
import io.github.secsdev.itmochan.service.VideoService
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
    private val pictureService : PictureService,
    private val videoService : VideoService,
) {
    @GetMapping("pic/{picture_id}")
    @ResponseBody fun getPicture(
        @PathVariable("picture_id") pictureId: UUID,
    ) : ResponseEntity<ByteArray> {
        val picture = pictureService.getPicture(pictureId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.valueOf(picture.picture.contentType)
        headers.contentDisposition =
            ContentDisposition.builder("inline").filename(picture.picture.name).build()

        return ResponseEntity.ok()
            .headers(headers)
            .body(picture.byteArray)
    }

    @GetMapping("vid/{video_id}")
    @ResponseBody fun getVideo(
        @PathVariable("video_id") videoId: UUID,
    ) : ResponseEntity<ByteArray> {
        val video = videoService.getVideo(videoId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.valueOf(video.video.contentType)
        headers.contentDisposition =
            ContentDisposition.builder("inline").filename(video.video.name).build()

        return ResponseEntity.ok()
            .headers(headers)
            .body(video.byteArray)
    }

    @DeleteMapping("pic/{picture_id}")
    fun deletePicture(
        @PathVariable("picture_id") pictureId: UUID,
    ) {
        return pictureService.deletePicture(pictureId)
    }

    @DeleteMapping("vid/{video_id}")
    fun deleteVideo(
        @PathVariable("video_id") videoId: UUID,
    ) {
        return videoService.deleteVideo(videoId)
    }
}