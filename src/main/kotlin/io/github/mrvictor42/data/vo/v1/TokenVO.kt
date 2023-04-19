package io.github.mrvictor42.data.vo.v1

import java.util.Date

data class TokenVO(
    val username : String? = null,
    val accessToken : String? = null,
    val refreshToken : String? = null,
    val authenticated : Boolean? = null,
    val created : Date? = null,
    val expiration : Date? = null
)
