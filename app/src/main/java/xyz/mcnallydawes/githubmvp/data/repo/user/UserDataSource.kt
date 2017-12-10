package xyz.mcnallydawes.githubmvp.data.repo.user

import io.reactivex.Observable
import xyz.mcnallydawes.githubmvp.data.model.User

interface UserDataSource : DataSource<User> {

    fun getAllUsers(lastUserId: Int) : Observable<ArrayList<User>>

}