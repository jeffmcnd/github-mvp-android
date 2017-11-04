package xyz.mcnallydawes.githubmvp.di

import dagger.Module
import dagger.Provides
import xyz.mcnallydawes.githubmvp.data.source.user.UserLocalDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRemoteDataSource
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository
import xyz.mcnallydawes.githubmvp.github.GithubApi
import javax.inject.Singleton

@Module
object RepoInjection {

    @Provides @Singleton
    fun provideUserRepo(githubApi: GithubApi): UserRepository {
        return UserRepository(UserLocalDataSource(), UserRemoteDataSource(githubApi))
    }

}