package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toMaybe
import io.reactivex.rxkotlin.toSingle
import xyz.mcnallydawes.githubmvp.data.model.local.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UserRepository @Inject constructor(
        private val localDataSource: UserDataSource,
        private val remoteDataSource: UserDataSource
): UserDataSource {


    override fun getAll(): Single<ArrayList<User>> {
        return localDataSource.getAll()
    }

    override fun getAllUsers(lastUserId: Int): Single<ArrayList<User>> {
        return localDataSource.getAllUsers(lastUserId)
                .flatMap {
                    if (it.isEmpty()) {
                        remoteDataSource.getAllUsers(lastUserId)
                                .flatMap { localDataSource.saveAll(it) }
                    }
                    else Single.just(it)
                }
    }

    override fun get(id: Int): Observable<User> {
        return remoteDataSource.get(id)
                .flatMap { localDataSource.save(it) }
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

    override fun save(obj: User): Observable<User> {
        return localDataSource.save(obj)
    }

    override fun remove(id: Int): Completable = localDataSource.remove(id)

    override fun removeAll(): Completable = localDataSource.removeAll()
}