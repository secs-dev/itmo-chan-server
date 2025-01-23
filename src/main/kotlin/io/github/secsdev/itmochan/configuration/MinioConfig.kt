package io.github.secsdev.itmochan.configuration

import io.minio.MakeBucketArgs
import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig {

    @Value("\${minio-client.url}")
    private lateinit var minioClientUrl: String
    @Value("\${minio-client.accessKey}")
    private lateinit var minioClientAccessKey: String
    @Value("\${minio-client.secretKey}")
    private lateinit var minioClientSecretKey: String
    @Value("\${minio-client.bucket.files}")
    private lateinit var minioClientBucketFiles: String

    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint(minioClientUrl)
        .credentials(minioClientAccessKey, minioClientSecretKey)
        .build().also {
            if (it.listBuckets().none { x -> x.name().equals(minioClientBucketFiles) }) {
                it.makeBucket(
                    MakeBucketArgs
                        .builder()
                        .bucket(minioClientBucketFiles)
                        .build())
            }

        }
}