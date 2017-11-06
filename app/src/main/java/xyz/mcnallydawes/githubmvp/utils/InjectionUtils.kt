package xyz.mcnallydawes.githubmvp.utils

import dagger.Module
import dagger.Provides
import xyz.mcnallydawes.githubmvp.BuildConfig
import xyz.mcnallydawes.githubmvp.di.NetInjection
import xyz.mcnallydawes.githubmvp.github.GithubApi

@Module
object InjectionUtils {

    @Provides
    fun getGithubApi() : GithubApi {
        val apiDomain = BuildConfig.API_DOMAIN
        return NetInjection.provideGithubApi(
                NetInjection.provideRetrofit(
                        apiDomain,
                        NetInjection.provideOkHttpClient(
                                20L
                        )
                )
        )
    }

}