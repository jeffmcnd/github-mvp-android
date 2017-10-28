package xyz.mcnallydawes.githubmvp.di

import android.content.Context
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.source.user.UserLocalDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRemoteDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository
import xyz.mcnallydawes.githubmvp.di.providers.ApiProvider
import xyz.mcnallydawes.githubmvp.di.providers.OkhttpProvider
import xyz.mcnallydawes.githubmvp.di.providers.RetroProvider
import xyz.mcnallydawes.githubmvp.github.GithubApi

object Injection {

    @Throws(IllegalStateException::class)
    fun provideOkHttpClient(): OkHttpClient {
        return OkhttpProvider.getInstance(20L).okHttpClient
    }

    @Throws(IllegalStateException::class)
    fun provideRetrofit(@NonNull apiDomain: String, @NonNull okHttpClient: OkHttpClient): Retrofit {
        checkNotNull(apiDomain)
        checkNotNull(okHttpClient)
        return RetroProvider.getInstance(apiDomain, okHttpClient).retrofit
    }

    @Throws(IllegalStateException::class)
    fun provideGithubApi(context: Context): GithubApi {
        val domain = context.resources.getString(R.string.api_domain)
        val retrofit = provideRetrofit(domain, provideOkHttpClient())
        checkNotNull(retrofit)
        return ApiProvider.getInstance(retrofit).githubApi
    }

    fun provideUserRepo(context: Context): UserRepository {
        val githubApi = provideGithubApi(context)
        checkNotNull(githubApi)
        return UserRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance(githubApi)
        )
    }

}