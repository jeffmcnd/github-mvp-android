package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface DataSource<I, T> {

    fun get(id: I) : Observable<T>

    fun getAll() : Single<ArrayList<T>>

    fun save(obj : T) : Observable<T>

    fun saveAll(objects: ArrayList<T>) : Single<ArrayList<T>>

    fun remove(id: I) : Completable

    fun removeAll() : Completable

}

