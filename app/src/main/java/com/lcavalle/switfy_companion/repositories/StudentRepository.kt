package com.lcavalle.switfy_companion.repositories

import com.lcavalle.switfy_companion.dataSources.Api42DataSource
import com.lcavalle.switfy_companion.dataClasses.Student
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val api: Api42DataSource
) {
     suspend fun getStudent(token: String, login: String): Student? {
        return api.getStudent(token, login)
    }
}