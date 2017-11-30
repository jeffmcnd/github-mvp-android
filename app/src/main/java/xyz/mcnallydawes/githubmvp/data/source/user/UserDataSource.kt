package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.Repo
import xyz.mcnallydawes.githubmvp.data.model.local.User

interface UserDataSource : DataSource<User> {

    fun getAllUsers(lastUserId: Int) : Observable<ArrayList<User>>

}