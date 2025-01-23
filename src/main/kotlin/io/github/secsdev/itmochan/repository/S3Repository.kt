package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.exception.MinioException
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class S3Repository(
    private val minioClient: MinioClient,
    @Value("\${minio-client.bucket.files}")
    private val minioClientBucketFiles: String
) {
    fun saveFile(filename:String, contentType: String, inputStream: InputStream) {
        try {
            minioClient.putObject(
                PutObjectArgs
                    .builder()
                    .bucket(minioClientBucketFiles)
                    .`object`(filename)
                    .contentType(contentType)
                    .stream(inputStream, inputStream.available().toLong(), -1).build())
        } catch (e: Exception) {
            throw MinioException(e.message?: UNKNOWN_EXC_STRING, e)
        }
    }

    fun getFileByteArray(filename: String) : ByteArray {
        try {
            minioClient.getObject(
                GetObjectArgs
                    .builder()
                    .bucket(minioClientBucketFiles)
                    .`object`(filename)
                    .build()
            ).use { stream -> return stream.readBytes() }
        } catch (e: Exception) {
            throw MinioException(e.message ?: UNKNOWN_EXC_STRING, e)
        }
    }

    fun deleteFile(filename: String) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs
                    .builder()
                    .bucket(minioClientBucketFiles)
                    .`object`(filename)
                    .build()
            )
        } catch (e: Exception) {
            throw MinioException(e.message ?: UNKNOWN_EXC_STRING, e)
        }
    }

    companion object {
        private const val UNKNOWN_EXC_STRING = "Unknown exception"
    }
}