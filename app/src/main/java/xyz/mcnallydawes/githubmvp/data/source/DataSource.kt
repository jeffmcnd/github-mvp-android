package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface DataSource<I, T> {

    fun get(id: I) : Maybe<T>

    fun getAll() : Single<ArrayList<T>>

    fun save(obj : T) : Single<T>

    fun saveAll(objects: ArrayList<T>) : Single<ArrayList<T>>

    fun remove(id: I) : Completable

    fun removeAll() : Completable

}

