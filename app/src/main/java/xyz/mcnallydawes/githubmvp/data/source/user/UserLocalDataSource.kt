package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User

class UserLocalDataSource: UserDataSource {

    companion object {
        private var INSTANCE: UserLocalDataSource? = null

        @JvmStatic fun getInstance(): UserLocalDataSource =
                INSTANCE ?: UserLocalDataSource().apply{ INSTANCE = this }

        @JvmStatic fun destroyInstance() { INSTANCE = null }
    }

    override fun getUsers(lastUserId: Int): Single<ArrayList<User>> {
//        TODO: implement
        return Single.error(Throwable("Unable to retrieve local results."))
    }

    override fun getUser(username: String): Single<User> {
//        TODO: implement
        return Single.error(Throwable("Unable to retrieve local results."))
    }
}