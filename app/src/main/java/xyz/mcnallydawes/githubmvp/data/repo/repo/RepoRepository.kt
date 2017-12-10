package xyz.mcnallydawes.githubmvp.data.repo.repo

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.Repo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class RepoRepository @Inject constructor(
        private val localDataSource: RepoDataSource,
        private val remoteDataSource: RepoDataSource
): RepoDataSource {

    override fun getUserRepos(userId: Int): Observable<ArrayList<Repo>> {
        return remoteDataSource.getUserRepos(userId)
                .flatMapSingle {
                    for (repo in it) {
                        repo.userId = userId
                    }
                    localDataSource.saveAll(it)
                }
                .publish { network ->
                    Observable.merge(
                            network,
                            localDataSource.getUserRepos(userId).takeUntil(network)
                    )
                }
    }

    override fun getAll(): Observable<ArrayList<Repo>> {
        return localDataSource.getAll()
    }

    override fun get(id: Int): Observable<Repo> {
        return remoteDataSource.get(id)
                .flatMapSingle { localDataSource.save(it) }
                .publish { network ->
                    Observable.merge(
                            network,
                            localDataSource.get(id).takeUntil(network)
                    )
                }
    }

    override fun saveAll(objects: ArrayList<Repo>): Single<ArrayList<Repo>> {
        return localDataSource.saveAll(objects)
    }

    override fun save(obj: Repo): Single<Repo> {
        return localDataSource.save(obj)
    }

    override fun remove(id: Int): Completable = localDataSource.remove(id)

    override fun removeAll(): Completable = localDataSource.removeAll()
}
