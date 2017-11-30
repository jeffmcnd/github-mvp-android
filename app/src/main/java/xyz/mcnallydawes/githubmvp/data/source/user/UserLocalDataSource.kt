package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import io.realm.Realm
import xyz.mcnallydawes.githubmvp.data.model.local.*
import javax.inject.Singleton

@Singleton
class UserLocalDataSource : UserDataSource {

    override fun getAllUsers(lastUserId: Int): Observable<ArrayList<User>> {
        return Observable.create {
            val users = ArrayList<User>()
            val realm = Realm.getDefaultInstance()
            val results = realm.where(User::class.java)
                    .greaterThan("id", lastUserId)
                    .findAll()
                    .sort("id")
            if (results != null) users.addAll(realm.copyFromRealm(results))
            realm.close()
            it.onNext(users)
            it.onComplete()
        }
    }

    override fun getAll() : Observable<ArrayList<User>> {
        return Observable.create {
            val users = ArrayList<User>()
            val realm = Realm.getDefaultInstance()
            val results = realm.where(User::class.java).findAll().sort("id")
            if (results != null) users.addAll(realm.copyFromRealm(results))
            realm.close()
            it.onNext(users)
            it.onComplete()
        }
    }

    override fun get(id: Int) : Observable<User> = User().get(id)

    override fun saveAll(objects: ArrayList<User>) : Single<ArrayList<User>> = User().saveAll(objects)

    override fun save(obj: User) : Single<User> = obj.save()

    override fun remove(id: Int): Completable = User().delete(id)

    override fun removeAll(): Completable = User().deleteAll()

}