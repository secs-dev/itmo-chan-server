package io.github.e1turin.itmochan.entity

data class Captcha(
    val capthchaId : Long,
    val answer : String?,
    val pictureId : Long,
)

