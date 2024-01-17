package io.github.e1turin.itmochan.entity

data class Captcha(
    val capthchaId : Int,
    val answer : String?,
    val pictureId : Int,
)
