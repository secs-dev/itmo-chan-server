package io.github.e1turin.itmochan.exception

import io.jsonwebtoken.JwtException

class WrongUsernameException(message: String) : JwtException(message)