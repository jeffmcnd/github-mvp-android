package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User

interface UserDataSource {

    fun getUsers(lastUserId: Int): Single<ArrayList<User>>

    fun getUser(username: String): Single<User>

}