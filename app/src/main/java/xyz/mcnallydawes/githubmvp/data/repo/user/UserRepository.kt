package xyz.mcnallydawes.githubmvp.data.repo.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UserRepository @Inject constructor(
        private val localDataSource: UserDataSource,
        private val remoteDataSource: UserDataSource
): UserDataSource {

    override fun getAll(): Observable<ArrayList<User>> {
        return localDataSource.getAll()
    }

    override fun getAllUsers(lastUserId: Int): Observable<ArrayList<User>> {
        return localDataSource.getAllUsers(lastUserId)
                .flatMap {
                    if (it.size == 0) {
                        remoteDataSource.getAllUsers(lastUserId)
                                .flatMapSingle { localDataSource.saveAll(it) }
                    } else Observable.just(it)
                }
    }

    override fun searchUsers(q: String, sort: String?, order: String?): Observable<ArrayList<User>> =
            remoteDataSource.searchUsers(q, sort, order)

    override fun get(id: Int): Observable<User> {
        return remoteDataSource.get(id)
                .flatMapSingle { localDataSource.save(it) }
                .publish { network ->
                    Observable.merge(
                            network,
                            localDataSource.get(id).takeUntil(network)
                    )
                }
    }

    override fun saveAll(objects: ArrayList<User>): Single<ArrayList<User>> {
        return localDataSource.saveAll(objects)
    }

    override fun save(obj: User): Single<User> {
        return localDataSource.save(obj)
    }

    override fun remove(id: Int): Completable = localDataSource.remove(id)

    override fun removeAll(): Completable = localDataSource.removeAll()
}