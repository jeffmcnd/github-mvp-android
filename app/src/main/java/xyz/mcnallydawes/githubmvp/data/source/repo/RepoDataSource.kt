package xyz.mcnallydawes.githubmvp.data.source.repo

import io.reactivex.Observable
import xyz.mcnallydawes.githubmvp.data.model.local.Repo
import xyz.mcnallydawes.githubmvp.data.source.user.DataSource

interface RepoDataSource : DataSource<Repo> {

    fun getUserRepos(userId : Int) : Observable<ArrayList<Repo>>

}
