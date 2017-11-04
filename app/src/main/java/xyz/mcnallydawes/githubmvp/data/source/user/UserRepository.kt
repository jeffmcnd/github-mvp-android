package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
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

    override fun get(id: Int): Maybe<User> = remoteDataSource.get(id)

    override fun saveAll(objects: ArrayList<User>): Single<ArrayList<User>> {
        return localDataSource.saveAll(objects)
    }

    override fun save(obj: User): Single<User> {
        return localDataSource.save(obj).flatMap { remoteDataSource.save(obj) }
    }

    override fun remove(id: Int): Completable = localDataSource.remove(id)

    override fun removeAll(): Completable = localDataSource.removeAll()
}