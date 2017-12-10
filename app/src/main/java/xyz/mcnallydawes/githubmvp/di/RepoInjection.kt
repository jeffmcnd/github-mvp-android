package xyz.mcnallydawes.githubmvp.di

import dagger.Module
import dagger.Provides
import xyz.mcnallydawes.githubmvp.data.repo.repo.RepoLocalDataSource
import xyz.mcnallydawes.githubmvp.data.repo.repo.RepoRemoteDataSource
import xyz.mcnallydawes.githubmvp.data.repo.repo.RepoRepository
import xyz.mcnallydawes.githubmvp.data.repo.user.UserLocalDataSource
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRemoteDataSource
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRepository
import xyz.mcnallydawes.githubmvp.data.api.GithubApi
import javax.inject.Singleton

@Module
object RepoInjection {

    @Provides @Singleton
    fun provideUserRepo(githubApi: GithubApi) : UserRepository {
        return UserRepository(UserLocalDataSource(), UserRemoteDataSource(githubApi))
    }

    @Provides @Singleton
    fun provideRepoRepo(githubApi: GithubApi) : RepoRepository {
        return RepoRepository(RepoLocalDataSource(), RepoRemoteDataSource(githubApi))
    }

}