package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.Repo
import xyz.mcnallydawes.githubmvp.data.model.local.User
import xyz.mcnallydawes.githubmvp.github.GithubApi
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