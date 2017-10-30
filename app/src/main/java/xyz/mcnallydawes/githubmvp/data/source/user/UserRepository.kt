package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User

open class UserRepository(
        private val localDataSource: UserDataSource,
        private val remoteDataSource: UserDataSource
): UserDataSource {

    companion object {
        private var INSTANCE: UserRepository? = null

        @JvmStatic
        fun getInstance(
                localDataSource: UserLocalDataSource,
                remoteDataSource: UserRemoteDataSource
        ): UserRepository =
                INSTANCE ?: UserRepository(localDataSource, remoteDataSource).apply { INSTANCE = this }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getUsers(lastUserId: Int): Single<ArrayList<User>> {
        return localDataSource.getUsers(lastUserId)
                .flatMap {
                    if (it.isEmpty()) {
                        remoteDataSource.getUsers(lastUserId)
                                .flatMap { localDataSource.saveUsers(it) }
                    }
                    else Single.just(it)
                }
    }

    override fun getUser(username: String): Maybe<User> {
        return remoteDataSource.getUser(username)
    }

    override fun saveUsers(users: ArrayList<User>): Single<ArrayList<User>> {
        return localDataSource.saveUsers(users)
    }

    override fun saveUser(user: User): Single<User> {
        return localDataSource.saveUser(user)
    }

    override fun removeUser(id: Int): Completable = localDataSource.removeUser(id)

    override fun removeAllUsers(): Completable = localDataSource.removeAllUsers()
}