package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User

interface UserDataSource {

    fun getUsers(lastUserId: Int) : Single<ArrayList<User>>

    fun getUser(username: String) : Maybe<User>

    fun saveUsers(users: ArrayList<User>) : Single<ArrayList<User>>

    fun saveUser(user: User) : Single<User>

    fun removeUser(id: Int) : Completable

    fun removeAllUsers() : Completable

}