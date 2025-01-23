package io.github.secsdev.itmochan.exception

open class StorageException(message: String, reason: Exception?) : RuntimeException(message, reason) {
    constructor(message: String): this(message, null)
}
