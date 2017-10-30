package xyz.mcnallydawes.githubmvp.utils

import android.content.Context
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.di.NetInjection
import xyz.mcnallydawes.githubmvp.github.GithubApi

object InjectionUtils {

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