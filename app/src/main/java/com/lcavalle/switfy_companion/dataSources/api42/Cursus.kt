package com.lcavalle.switfy_companion.dataSources.api42

import com.squareup.moshi.Json


class Cursus(
    @Json(name = "skills") val skills: List<Skill>
) {
    companion object {
        class Skill(
            @Json(name = "name") val name: String?,
            @Json(name = "level") val level: Float,
        ) {
            override fun toString(): String {
                return "name: ${name}, lvl: ${level}"
            }
        }

    }
}