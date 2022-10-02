package com.lcavalle.switfy_companion.dataSources.api42

import com.squareup.moshi.Json

class AccessTokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires_in") val lifespan: Long,
    @Json(name = "created_at") val birth: Long
)
