package xyz.mcnallydawes.githubmvp.data.model.local

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmObject

fun <T : RealmObject> T.get(id : Int) : Observable<T> {
    return Observable.create {
        val realm = Realm.getDefaultInstance()
        var obj: T? = null
        val objResult = realm.where(this::class.java).equalTo("id", id).findFirst()
        if (objResult != null) obj = realm.copyFromRealm(objResult)
        realm.close()
        if (obj != null) it.onNext(obj)
        it.onComplete()
    }
}

fun <T : RealmObject> T.get(id : String) : Observable<T> {
    return Observable.create {
        val realm = Realm.getDefaultInstance()
        var obj: T? = null
        val objResult = realm.where(this::class.java).equalTo("id", id).findFirst()
        if (objResult != null) obj = realm.copyFromRealm(objResult)
        realm.close()
        if (obj != null) it.onNext(obj)
        it.onComplete()
    }
}

fun  <T : RealmObject> T.save(): Observable<T> {
    return Observable.create {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { it.copyToRealmOrUpdate(this) }
        realm.close()
        it.onNext(this)
        it.onComplete()
    }
}

fun  <T : RealmObject> T.saveAll(objects : ArrayList<T>): Single<ArrayList<T>> {
    return Single.create {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(objects)
        }
        realm.close()
        it.onSuccess(objects)
    }
}

fun <T : RealmObject> T.delete(id: Int): Completable {
    return Completable.create {
        val realm = Realm.getDefaultInstance()
        val objResult = realm.where(this::class.java).equalTo("id", id).findFirst()
        if (objResult != null) { realm.executeTransaction { objResult.deleteFromRealm() } }
        realm.close()
        it.onComplete()
    }
}

fun <T : RealmObject> T.deleteAll(): Completable {
    return Completable.create {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(this::class.java).findAll()
        realm.executeTransaction { results.deleteAllFromRealm() }
        realm.close()
        it.onComplete()
    }
}

fun <T : RealmObject> T.all(): Single<ArrayList<T>> {
    return Single.create {
        val objects = ArrayList<T>()
        val realm = Realm.getDefaultInstance()
        val results = realm.where(this::class.java).findAll()
        if (results != null) objects.addAll(realm.copyFromRealm(results))
        realm.close()
        it.onSuccess(objects)
    }
}

fun <T : RealmObject> T.first(): Maybe<T> {
    return Maybe.create {
        val realm = Realm.getDefaultInstance()
        val obj = realm.where(this::class.java).findFirst()
        realm.close()
        if (obj == null) it.onComplete()
        else it.onSuccess(obj)
    }
}

