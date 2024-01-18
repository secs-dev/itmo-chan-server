package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Captcha")
data class Captcha(
    val capthchaId : Long,
    val answer : String?,
    val pictureId : Long,
)
