package xyz.mcnallydawes.githubmvp.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.mcnallydawes.githubmvp.github.GithubApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetInjection {

    @Provides @Singleton
    @Throws(IllegalStateException::class)
    fun provideOkHttpClient(timeOut: Long): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                // For Testing
                //.addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) )
                .build()
    }

    @Provides @Singleton
    @Throws(IllegalStateException::class)
    fun provideRetrofit(apiDomain: String, okHttpClient: OkHttpClient): Retrofit {
                return Retrofit.Builder()
                        .baseUrl(apiDomain)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()

    }

    @Provides @Singleton
    @Throws(IllegalStateException::class)
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

}