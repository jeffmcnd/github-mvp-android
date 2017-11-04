package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User

interface UserDataSource : DataSource<Int, User> {

    fun getAllUsers(lastUserId: Int) : Single<ArrayList<User>>

}