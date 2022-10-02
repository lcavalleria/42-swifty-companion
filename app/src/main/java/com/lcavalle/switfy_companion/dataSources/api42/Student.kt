package com.lcavalle.switfy_companion.dataSources.api42

import com.squareup.moshi.Json

class Student(
    @Json(name = "email") val email: String?,
    @Json(name = "login") val login: String?,
    @Json(name = "usual_full_name") val fullName: String? = "",
    @Json(name = "image_url") val imageUrl: String? = "",
    @Json(name = "correction_point") val correctionPoints: Int = 0,
    @Json(name = "wallet") val wallet: Int = 0
) {
    override fun toString(): String {
        return "email: $email\n" +
                "login: $login\n" +
                "name: $fullName\n" +
                "imageUrl: $imageUrl\n" +
                "correctionPoints: $correctionPoints\n" +
                "wallet: $wallet\n"
    }

    companion object {
        val Example = Student(
            email = "-----@----.--",
            login = "-----",
            fullName = "---- ------",
            imageUrl = "https://------.--/---",
            correctionPoints = 0,
            wallet = 0
        )
    }


}