package xyz.mcnallydawes.githubmvp.github

import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.mcnallydawes.githubmvp.data.model.local.User

interface GithubApi {

    @GET("/users")
    fun getUsers(@Query("since") lastUserId: Int): Single<ArrayList<User>>

    @GET("/users/{id}")
    fun getUser(@Path("id") username: Int): Maybe<User>

}