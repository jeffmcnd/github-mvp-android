package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User

open class UserRepository(
        private val localDataSource: UserLocalDataSource,
        private val remoteDataSource: UserRemoteDataSource
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
//        TODO: implement local storage
        return remoteDataSource.getUsers(lastUserId)
    }

    override fun getUser(username: String): Single<User> {
//        TODO: implement local storage
        return remoteDataSource.getUser(username)
    }

}