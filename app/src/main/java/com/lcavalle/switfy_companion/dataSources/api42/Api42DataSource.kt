package com.lcavalle.switfy_companion.dataSources.api42

import retrofit2.http.*

interface Api42DataSource {
    /**
     * Will return a list containing only the student of "login" or empty
     */
    @GET("/v2/users")
    suspend fun getStudent(
        @Header("Authorization") authHeader: String,
        @Query("filter[login]") login: String
    ): List<Student>

    @GET("/v2/users/{id}/cursus_users")
    suspend fun getCursus(
        @Header("Authorization") authHeader: String,
        @Path("id") studentId: Int,
        @Query("filter[cursus_id]") cursusId: Int,
    ): List<Cursus>

    @GET("/v2/users/{id}/projects_users")
    suspend fun getStudentProjects(
        @Header("Authorization") authHeader: String,
        @Path("id") studentId: Int,
        @Query("page") page: Int
    ): List<StudentProject>

    @POST("/oauth/token")
    suspend fun postTokenRequest(
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): AccessTokenResponse

    companion object {
        const val grantType: String = "client_credentials"
        const val cursusId: Int = 21 // current cursus. We don't care about old cursus or piscine
        private const val tokenType: String = "Bearer"
        fun buildAuthorizationString(token: String) = "$tokenType $token"
    }
}