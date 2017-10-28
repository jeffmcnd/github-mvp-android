package xyz.mcnallydawes.githubmvp.di.providers

import retrofit2.Retrofit
import xyz.mcnallydawes.githubmvp.github.GithubApi

class ApiProvider private constructor(retrofit: Retrofit) {

    val githubApi: GithubApi = retrofit.create(GithubApi::class.java)

    companion object {
        private var INSTANCE: ApiProvider? = null

        @JvmStatic fun getInstance(retrofit: Retrofit): ApiProvider =
                INSTANCE ?: ApiProvider(retrofit).apply { INSTANCE = this }
    }
}