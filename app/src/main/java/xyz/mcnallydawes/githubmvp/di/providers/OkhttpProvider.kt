package xyz.mcnallydawes.githubmvp.di.providers

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkhttpProvider private constructor(timeOut: Long) {

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            // For Testing
            //.addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) )
            .build()

    companion object {

        private var INSTANCE: OkhttpProvider? = null

        @JvmStatic fun getInstance(timeOut: Long): OkhttpProvider =
                INSTANCE ?: OkhttpProvider(timeOut).apply { INSTANCE = this }
    }
}