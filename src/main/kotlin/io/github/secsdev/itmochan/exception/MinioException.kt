package io.github.secsdev.itmochan.exception

class MinioException(message : String, reason: Exception?) : StorageException(message, reason) {
    constructor(message: String): this(message, null)
}
