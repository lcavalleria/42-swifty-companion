package com.lcavalle.switfy_companion.repositories

import android.util.Log
import com.lcavalle.switfy_companion.Credentials
import com.lcavalle.switfy_companion.SwiftyCompanion
import com.lcavalle.switfy_companion.dataSources.api42.AccessTokenResponse
import com.lcavalle.switfy_companion.dataSources.api42.Api42DataSource
import com.lcavalle.switfy_companion.dataSources.api42.Student
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
        if (token == "" || Instant.now().toEpochMilli() > tokenBirth + tokenLifespan) {
            Log.d(SwiftyCompanion.TAG, "no token or expired, getting a new one")
            postTokenRequest()
            Log.d(SwiftyCompanion.TAG, "token requested")
        }
        val student: Student? =
            api.getStudent(Api42DataSource.buildAuthorizationString(token), login)
                .elementAtOrNull(0)
        Log.d(SwiftyCompanion.TAG, student.toString())
        return@withContext student
    }

    private suspend fun postTokenRequest() = withContext(Dispatchers.IO) {
        val response: AccessTokenResponse =
            api.postTokenRequest(Api42DataSource.grantType, clientId, clientSecret)
        token = response.accessToken
        tokenLifespan = response.lifespan
        tokenBirth = response.birth

    }
}