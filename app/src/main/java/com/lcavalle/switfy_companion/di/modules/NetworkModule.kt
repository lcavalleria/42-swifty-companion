package com.lcavalle.switfy_companion.di.modules

import com.lcavalle.switfy_companion.dataSources.api42.Api42DataSource
import com.lcavalle.switfy_companion.dataSources.api42.Api42Interceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    val url = "https://api.intra.42.fr"

    @Provides
    @Singleton
    fun provideApi42DataSource(): Api42DataSource {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val okHttpClient: OkHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(Api42Interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(Api42DataSource::class.java)
    }
}