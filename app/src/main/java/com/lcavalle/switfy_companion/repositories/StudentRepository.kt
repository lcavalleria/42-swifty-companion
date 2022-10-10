package com.lcavalle.switfy_companion.repositories

import android.util.Log
import com.lcavalle.switfy_companion.Credentials
import com.lcavalle.switfy_companion.SwiftyCompanion
import com.lcavalle.switfy_companion.dataSources.api42.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val api: Api42DataSource
) {
    private var clientId = Credentials.UID
    private var clientSecret = Credentials.Secret
    private var token: String = ""
    private var tokenLifespan: Long = 0
    private var tokenBirth: Long = 0

    suspend fun getStudent(login: String): Student? = withContext(Dispatchers.IO) {
        updateTokenIfNeeded()
        val student: Student? =
            api.getStudent(
                Api42DataSource.buildAuthorizationString(token),
                login
            ).elementAtOrNull(0)
        if (student != null) {
            val cursus: Cursus? =
                api.getCursus(
                    Api42DataSource.buildAuthorizationString(token),
                    student.id,
                    Api42DataSource.cursusId
                ).elementAtOrNull(0)
            student.cursus = cursus
        }
        Log.d(SwiftyCompanion.TAG, student.toString())
        return@withContext student
    }


    suspend fun getStudentProjects(studentId: Int, page: Int): List<StudentProject> =
        withContext(Dispatchers.IO) {
            updateTokenIfNeeded()
            val projects: List<StudentProject> =
                api.getStudentProjects(
                    Api42DataSource.buildAuthorizationString(token),
                    studentId,
                    page
                )
            return@withContext projects
        }


    private suspend fun updateTokenIfNeeded() = withContext(Dispatchers.IO) {
        Log.d(
            SwiftyCompanion.TAG,
            "check if token is too old -- Token: $token, now: ${
                Instant.now().epochSecond
            }, token expires at: ${tokenBirth + tokenLifespan}"
        )
        if (token == "" || Instant.now().epochSecond > tokenBirth + tokenLifespan) {
            Log.d(SwiftyCompanion.TAG, "no token or expired, getting a new one")
            val response: AccessTokenResponse =
                api.postTokenRequest(Api42DataSource.grantType, clientId, clientSecret)
            token = response.accessToken
            tokenLifespan = response.lifespan
            tokenBirth = response.birth
            Log.d(SwiftyCompanion.TAG, "token updated")
        }
    }
}