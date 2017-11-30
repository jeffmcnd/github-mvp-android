package xyz.mcnallydawes.githubmvp.data.source.repo

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.Repo
import xyz.mcnallydawes.githubmvp.github.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRemoteDataSource @Inject constructor(private val githubApi: GithubApi) : RepoDataSource {

    override fun getAll(): Observable<ArrayList<Repo>> = Observable.error(Throwable("not implemented"))

    override fun get(id: Int): Observable<Repo> = Observable.error(Throwable("not implemented"))

    override fun saveAll(objects: ArrayList<Repo>): Single<ArrayList<Repo>> =
            Single.error(Throwable("not implemented"))

    override fun save(obj: Repo): Single<Repo> = Single.error(Throwable("not implemented"))

    override fun remove(id: Int): Completable = Completable.error(Throwable("not implemented"))

    override fun removeAll(): Completable = Completable.error(Throwable("not implemented"))

    override fun getUserRepos(userId: Int): Observable<ArrayList<Repo>> {
        return Observable.create { emitter ->
            githubApi.getUserRepos(userId)
                    .subscribe({
                        emitter.onNext(it)
                        emitter.onComplete()
                    }, {
                        emitter.onNext(ArrayList())
                        emitter.onComplete()
                    })
        }
    }

}
