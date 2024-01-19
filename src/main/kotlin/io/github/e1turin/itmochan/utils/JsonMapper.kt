package io.github.e1turin.itmochan.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

@Throws(JsonProcessingException::class)
fun convertObjectToJson(o: Any?): String {
    if (o == null) {
        return ""
    }
    val mapper = ObjectMapper()
    return mapper.writeValueAsString(o)
}