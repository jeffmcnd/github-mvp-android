package xyz.mcnallydawes.githubmvp.data.repo.repo

import io.reactivex.Observable
import xyz.mcnallydawes.githubmvp.data.model.Repo
import xyz.mcnallydawes.githubmvp.data.repo.user.DataSource

interface RepoDataSource : DataSource<Repo> {

    fun getUserRepos(userId : Int) : Observable<ArrayList<Repo>>

}
