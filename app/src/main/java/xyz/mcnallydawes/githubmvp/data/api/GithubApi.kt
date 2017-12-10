package xyz.mcnallydawes.githubmvp.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.mcnallydawes.githubmvp.data.model.Repo
import xyz.mcnallydawes.githubmvp.data.model.SearchUsersResponse
import xyz.mcnallydawes.githubmvp.data.model.User

interface GithubApi {

    @GET("/users")
    fun getUsers(@Query("since") lastUserId : Int): Observable<ArrayList<User>>

    @GET("/user/{id}")
    fun getUser(@Path("id") id : Int): Observable<User>

    @GET("/user/{id}/repos?sort=updated")
    fun getUserRepos(@Path("id") id : Int) : Observable<ArrayList<Repo>>

    @GET("/search/users")
    fun searchUsers(
            @Query("q") q: String,
            @Query("sort") sort: String?,
            @Query("order") order: String?) : Observable<SearchUsersResponse>

}