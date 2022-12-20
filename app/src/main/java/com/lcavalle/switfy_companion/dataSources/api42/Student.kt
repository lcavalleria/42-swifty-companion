package com.lcavalle.switfy_companion.dataSources.api42

import com.squareup.moshi.Json

class Student(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "email") val email: String?,
    @Json(name = "login") val login: String?,
    @Json(name = "usual_full_name") val fullName: String? = "",
    @Json(name = "image") val image: StudentImage? = StudentImage(),
    @Json(name = "correction_point") val correctionPoints: Int = 0,
    @Json(name = "wallet") val wallet: Int = 0
) {

    class StudentImage(
        @Json(name = "link") val link: String? = ""
    )

    @Transient
    var cursus: Cursus? = null

    val imageLink: String? = image?.link

    override fun toString(): String {
        return "id: $id\n" +
                "email: $email\n" +
                "login: $login\n" +
                "name: $fullName\n" +
                "imageLink: $imageLink\n" +
                "correctionPoints: $correctionPoints\n" +
                "wallet: $wallet\n" +
                "cursus: $cursus\n"
    }
}