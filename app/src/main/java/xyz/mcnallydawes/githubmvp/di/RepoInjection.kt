package xyz.mcnallydawes.githubmvp.di

import android.content.Context
import xyz.mcnallydawes.githubmvp.data.source.user.UserLocalDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRemoteDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository
import xyz.mcnallydawes.githubmvp.github.GithubApi
import xyz.mcnallydawes.githubmvp.utils.InjectionUtils

object RepoInjection {

    fun provideUserRepo(githubApi: GithubApi): UserRepository {
        checkNotNull(githubApi)
        return UserRepository.getInstance(
                UserLocalDataSource,
                UserRemoteDataSource.getInstance(githubApi)
        )
    }

}