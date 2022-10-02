package com.lcavalle.switfy_companion.dataSources.api42

import android.util.Log
import com.lcavalle.switfy_companion.SwiftyCompanion
import okhttp3.Interceptor
import okhttp3.Response

object Api42Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.body()
        Log.d(
            SwiftyCompanion.TAG,
            "\nOutgoing request: {\n" +
                    "\turl: ${request.url()}\n" +
                    "\theaders: ${request.headers()}\n" +
                    "\tbody: ${request.body()}\n"
        )
        return chain.proceed(request)
    }
}