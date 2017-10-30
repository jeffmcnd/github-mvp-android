package xyz.mcnallydawes.githubmvp.di

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.github.GithubApi
import java.util.concurrent.TimeUnit

object NetInjection {

    private var cachedOkHttpClient : OkHttpClient? = null
    private var cachedRetrofitMap : HashMap<String, Retrofit> = HashMap()
    private var cachedGithubApi : GithubApi? = null

    @Throws(IllegalStateException::class)
    private fun provideOkHttpClient(): OkHttpClient {
        return cachedOkHttpClient ?: OkHttpClient.Builder()
                .readTimeout(20L, TimeUnit.SECONDS)
                .connectTimeout(20L, TimeUnit.SECONDS)
                // For Testing
                //.addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) )
                .build()
                .apply { cachedOkHttpClient = this }
    }

    @Throws(IllegalStateException::class)
    private fun provideRetrofit(apiDomain: String, okHttpClient: OkHttpClient): Retrofit {
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
    fun provideGithubApi(context: Context): GithubApi {
        val domain = context.resources.getString(R.string.api_domain)
        return cachedGithubApi ?:
                provideRetrofit(domain, provideOkHttpClient())
                        .create(GithubApi::class.java)
                        .apply { cachedGithubApi = this }
    }

}