package com.lcavalle.switfy_companion.dataSources.api42

import com.squareup.moshi.Json

class StudentProject(
    @Json(name = "final_mark") val finalMark: Int?,
    @Json(name = "status") val status: String?,
    @Json(name = "validated?") val validated: Boolean?,
    @Json(name = "project") val project: Project?
) {
    companion object {
        class Project(
            @Json(name = "name") val name: String?
        )
    }
}