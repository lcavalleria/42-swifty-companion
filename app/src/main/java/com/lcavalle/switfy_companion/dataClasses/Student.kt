package com.lcavalle.switfy_companion.dataClasses

import java.time.LocalDate

class Student(
    val login: String?,
    val fullName: String? = null,
    val birthday: LocalDate? = null,
    val xp: Int = 0
) {


}