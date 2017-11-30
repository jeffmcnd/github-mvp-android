package xyz.mcnallydawes.githubmvp.data.source.repo

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import io.realm.Sort
import xyz.mcnallydawes.githubmvp.data.model.local.*
import javax.inject.Singleton


@Singleton
class RepoLocalDataSource : RepoDataSource {

    override fun getAll() : Observable<ArrayList<Repo>> {
        return Observable.create {
            val repos = ArrayList<Repo>()
            val realm = Realm.getDefaultInstance()
            val results = realm.where(Repo::class.java).findAll().sort("id")
            if (results != null) repos.addAll(realm.copyFromRealm(results))
            realm.close()
            it.onNext(repos)
            it.onComplete()
        }
    }

    override fun get(id: Int) : Observable<Repo> = Repo().get(id)

    override fun saveAll(objects: ArrayList<Repo>) : Single<ArrayList<Repo>> = Repo().saveAll(objects)

    override fun save(obj: Repo) : Single<Repo> = obj.save()

    override fun remove(id: Int): Completable = Repo().delete(id)

    override fun removeAll(): Completable = Repo().deleteAll()

    override fun getUserRepos(userId: Int): Observable<ArrayList<Repo>> {
        return Observable.create {
            val objects = ArrayList<Repo>()
            val realm = Realm.getDefaultInstance()
            val results = realm.where(Repo::class.java)
                    .equalTo("userId", userId)
                    .findAll()
                    .sort("updatedAt", Sort.DESCENDING)
            if (results != null) objects.addAll(realm.copyFromRealm(results))
            realm.close()
            it.onNext(objects)
            it.onComplete()
        }
    }

}
