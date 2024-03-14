package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Captcha")
data class Captcha(
    val captchaId : Long,
    val answer : String?,
    val pictureId : Long,
)

