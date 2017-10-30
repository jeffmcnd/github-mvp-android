package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User
import xyz.mcnallydawes.githubmvp.github.GithubApi

class UserRemoteDataSource private constructor(
        private val githubApi: GithubApi
): UserDataSource {

    companion object {
        private var INSTANCE: UserRemoteDataSource? = null

        @JvmStatic fun getInstance(githubApi: GithubApi): UserRemoteDataSource =
                INSTANCE ?: UserRemoteDataSource(githubApi).apply{ INSTANCE = this }

        @JvmStatic fun destroyInstance() { INSTANCE = null }
    }

    override fun getUsers(lastUserId: Int): Single<ArrayList<User>> = githubApi.getUsers(lastUserId)

    override fun getUser(username: String): Maybe<User> = githubApi.getUser(username)

    override fun saveUsers(users: ArrayList<User>): Single<ArrayList<User>> =
            Single.error(Throwable("not implemented"))

    override fun saveUser(user: User): Single<User> = Single.error(Throwable("not implemented"))

    override fun removeUser(id: Int): Completable = Completable.error(Throwable("not implemented"))

    override fun removeAllUsers(): Completable = Completable.error(Throwable("not implemented"))
}