package xyz.mcnallydawes.githubmvp.di

import android.content.Context
import xyz.mcnallydawes.githubmvp.data.source.user.UserLocalDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRemoteDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository

object RepoInjection {

    fun provideUserRepo(context: Context): UserRepository {
        val githubApi = NetInjection.provideGithubApi(context)
        checkNotNull(githubApi)
        return UserRepository.getInstance(
                UserLocalDataSource,
                UserRemoteDataSource.getInstance(githubApi)
        )
    }

}