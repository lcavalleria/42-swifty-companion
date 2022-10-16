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
    private var tokenExpiresAt: Long = 0
    private var lastRequestTimestampMs: Long = 0
    private val requestCooldown: Long = 500 // will be more than that to avoid race condition

    suspend fun getStudent(login: String): Student? = withContext(Dispatchers.IO) {
        if (isRateLimited()) return@withContext null
        updateTokenIfNeeded()
        lastRequestTimestampMs = Instant.now().toEpochMilli() // to avoid race condition
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
        lastRequestTimestampMs = Instant.now().toEpochMilli()
        return@withContext student
    }


    suspend fun getStudentProjects(studentId: Int, page: Int): List<StudentProject>? =
        withContext(Dispatchers.IO) {
            if (isRateLimited()) return@withContext null
            lastRequestTimestampMs = Instant.now().toEpochMilli() // to avoid race condition
            updateTokenIfNeeded()
            val projects: List<StudentProject> =
                api.getStudentProjects(
                    Api42DataSource.buildAuthorizationString(token),
                    studentId,
                    page
                )
            lastRequestTimestampMs = Instant.now().toEpochMilli()
            return@withContext projects
        }


    private suspend fun updateTokenIfNeeded() = withContext(Dispatchers.IO) {
        val now = Instant.now().epochSecond
        if (token == "" || now > tokenExpiresAt) {
            Log.d(SwiftyCompanion.TAG, "no token or expired, getting a new one")
            val response: AccessTokenResponse =
                api.postTokenRequest(Api42DataSource.grantType, clientId, clientSecret)
            token = response.accessToken
            tokenExpiresAt = now + response.expiresIn
            Log.d(SwiftyCompanion.TAG, "token updated")
        }
    }

    private fun isRateLimited(): Boolean {
        val now: Long = Instant.now().toEpochMilli()
        val waitingTime = lastRequestTimestampMs + requestCooldown - now
        val isRateLimited = waitingTime in 0..requestCooldown
        if (isRateLimited) Log.d(SwiftyCompanion.TAG, "request is rate limited")
        return isRateLimited
    }
}