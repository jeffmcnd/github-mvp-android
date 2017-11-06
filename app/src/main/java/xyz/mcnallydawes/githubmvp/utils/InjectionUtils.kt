package xyz.mcnallydawes.githubmvp.utils

import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.di.NetInjection
import xyz.mcnallydawes.githubmvp.github.GithubApi

@Module
object InjectionUtils {

    @Provides
    fun getGithubApi(context: Context) : GithubApi {
        val apiDomain = context.resources.getString(R.string.api_domain)
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