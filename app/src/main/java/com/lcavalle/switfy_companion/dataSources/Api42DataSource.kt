package com.lcavalle.switfy_companion.dataSources

import com.lcavalle.switfy_companion.dataClasses.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class Api42DataSource @Inject constructor() {
    suspend fun getStudent(token: String, login: String): Student? = withContext(Dispatchers.IO) {
        // todo: call 42 api to get the student
        return@withContext if (token != "")
            Student(login, "Example Student", LocalDate.parse("1992-12-03"))
        else
            null
    }
}
