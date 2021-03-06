package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import xyz.mcnallydawes.githubmvp.data.model.local.User
import xyz.mcnallydawes.githubmvp.github.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val githubApi: GithubApi) : UserDataSource {

    override fun getAllUsers(lastUserId: Int): Single<ArrayList<User>> = githubApi.getUsers(lastUserId)

    override fun getAll(): Single<ArrayList<User>> = Single.error(Throwable("not implemented"))

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

    override fun save(obj: User): Observable<User> = Observable.error(Throwable("not implemented"))

    override fun remove(id: Int): Completable = Completable.error(Throwable("not implemented"))

    override fun removeAll(): Completable = Completable.error(Throwable("not implemented"))

}