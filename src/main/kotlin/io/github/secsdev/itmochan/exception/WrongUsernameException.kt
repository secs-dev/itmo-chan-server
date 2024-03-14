package io.github.secsdev.itmochan.exception

import io.jsonwebtoken.JwtException

class WrongUsernameException(message: String) : JwtException(message)