package xyz.mcnallydawes.githubmvp.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.mcnallydawes.githubmvp.github.GithubApi
import java.util.concurrent.TimeUnit

object NetInjection {

    private var cachedOkHttpClient : HashMap<Long, OkHttpClient> = HashMap()
    private var cachedRetrofitMap : HashMap<String, Retrofit> = HashMap()
    private var cachedGithubApi : GithubApi? = null

    @Throws(IllegalStateException::class)
    fun provideOkHttpClient(timeOut: Long): OkHttpClient {
        return cachedOkHttpClient[timeOut] ?: OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                // For Testing
                //.addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) )
                .build()
                .apply { cachedOkHttpClient[timeOut] = this }
    }

    @Throws(IllegalStateException::class)
    fun provideRetrofit(apiDomain: String, okHttpClient: OkHttpClient): Retrofit {
        return cachedRetrofitMap[apiDomain] ?:
                Retrofit.Builder()
                        .baseUrl(apiDomain)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()
                        .apply { cachedRetrofitMap[apiDomain] = this }

    }

    @Throws(IllegalStateException::class)
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return cachedGithubApi ?:
                retrofit.create(GithubApi::class.java)
                        .apply { cachedGithubApi = this }
    }

}