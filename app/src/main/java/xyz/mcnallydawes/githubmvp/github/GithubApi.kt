package xyz.mcnallydawes.githubmvp.github

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.mcnallydawes.githubmvp.data.model.local.Repo
import xyz.mcnallydawes.githubmvp.data.model.local.User

interface GithubApi {

    @GET("/users")
    fun getUsers(@Query("since") lastUserId : Int): Observable<ArrayList<User>>

    @GET("/user/{id}")
    fun getUser(@Path("id") id : Int): Observable<User>

    @GET("/user/{id}/repos?sort=updated")
    fun getUserRepos(@Path("id") id : Int) : Observable<ArrayList<Repo>>

}