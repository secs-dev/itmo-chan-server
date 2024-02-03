package io.github.e1turin.itmochan.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


@Service
class PictureService {

    private val rootLocation = Paths.get("upload-dir")
    fun store(file: MultipartFile) {
        try {
            if (file.isEmpty) {
                throw IOException("Failed to store empty file.")
            }
            val destinationFile: Path = this.rootLocation.resolve(
                Paths.get(file.originalFilename)
            )
                .normalize().toAbsolutePath()
            if (!destinationFile.parent.equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw IOException(
                    "Cannot store file outside current directory."
                )
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        } catch (e: IOException) {
            throw IOException("Failed to store file.", e)
        }
    }
}
