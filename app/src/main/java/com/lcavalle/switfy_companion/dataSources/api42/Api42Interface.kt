package com.lcavalle.switfy_companion.dataSources.api42

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Api42Interface {
    /**
     * Will return a list containing only the student of "login" or empty
     */
    @GET("/v2/users")
    suspend fun getStudent(
        @Header("Authorization") authHeader: String,
        @Query("filter[login]") login: String
    ): List<Student>


    @POST("/oauth/token")
    suspend fun postTokenRequest(
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): AccessTokenResponse

    companion object {
        const val grantType: String = "client_credentials"
        private const val tokenType: String = "Bearer"
        fun buildAuthorizationString(token: String) = "$tokenType $token"
    }
}