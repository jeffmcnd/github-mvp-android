package xyz.mcnallydawes.githubmvp.data.repo.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DataSource<T> {

    fun get(id: Int) : Observable<T>

    fun getAll() : Observable<ArrayList<T>>

    fun save(obj : T) : Single<T>

    fun saveAll(objects: ArrayList<T>) : Single<ArrayList<T>>

    fun remove(id: Int) : Completable

    fun removeAll() : Completable

}

