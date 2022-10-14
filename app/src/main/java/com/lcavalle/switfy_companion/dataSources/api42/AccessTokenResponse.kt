package com.lcavalle.switfy_companion.dataSources.api42

import com.squareup.moshi.Json

class AccessTokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires_in") val expiresIn: Long,
)
