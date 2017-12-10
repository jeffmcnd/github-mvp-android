package xyz.mcnallydawes.githubmvp.data.repo.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.data.api.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val githubApi: GithubApi) : UserDataSource {

    override fun getAllUsers(lastUserId: Int): Observable<ArrayList<User>> {
        return Observable.create { emitter ->
            githubApi.getUsers(lastUserId)
                    .subscribe({
                        emitter.onNext(it)
                        emitter.onComplete()
                    }, {
                        emitter.onComplete()
                    }, {
                        emitter.onComplete()
                    })
        }
    }

    override fun searchUsers(q: String, sort: String?, order: String?): Observable<ArrayList<User>> =
            githubApi.searchUsers(q, sort, order)
                    .map { it.items }

    override fun getAll(): Observable<ArrayList<User>> = Observable.error(Throwable("not implemented"))

    override fun get(id: Int): Observable<User> {
        return Observable.create { emitter ->
            githubApi.getUser(id)
                    .subscribe({
                        emitter.onNext(it)
                        emitter.onComplete()
                    }, {
                        emitter.onComplete()
                    }, {
                        emitter.onComplete()
                    })
        }
    }

    override fun saveAll(objects: ArrayList<User>): Single<ArrayList<User>> =
            Single.error(Throwable("not implemented"))

    override fun save(obj: User): Single<User> = Single.error(Throwable("not implemented"))

    override fun remove(id: Int): Completable = Completable.error(Throwable("not implemented"))

    override fun removeAll(): Completable = Completable.error(Throwable("not implemented"))

}