package xyz.mcnallydawes.githubmvp.data.source.user

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.realm.Realm
import xyz.mcnallydawes.githubmvp.data.model.local.User

class UserLocalDataSource private constructor(): UserDataSource {

    companion object {
        private var INSTANCE: UserLocalDataSource? = null

        @JvmStatic fun getInstance(): UserLocalDataSource =
                INSTANCE ?: UserLocalDataSource().apply{ INSTANCE = this }

        @JvmStatic fun destroyInstance() { INSTANCE = null }
    }

    override fun getUsers(lastUserId: Int) : Single<ArrayList<User>> {
        return Single.create {
            val users = ArrayList<User>()
            val realm = Realm.getDefaultInstance()
            val results = realm.where(User::class.java)
                    .greaterThan("id", lastUserId)
                    .findAll()
                    .sort("id")
            if (results != null) users.addAll(realm.copyFromRealm(results))
            realm.close()
            it.onSuccess(users)
        }
    }

    override fun getUser(username: String) : Maybe<User> {
        return Maybe.create {
            val realm = Realm.getDefaultInstance()
            var user: User? = null
            val userResult = realm.where(User::class.java).equalTo("username", username).findFirst()
            if (userResult != null) user = realm.copyFromRealm(userResult)
            realm.close()
            if (user != null) it.onSuccess(user)
            else it.onComplete()
        }
    }

    override fun saveUsers(users: ArrayList<User>) : Single<ArrayList<User>> {
        return Single.create {
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                it.copyToRealmOrUpdate(users)
            }
            realm.close()
            it.onSuccess(users)
        }
    }

    override fun saveUser(user: User) : Single<User> {
        return Single.create {
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                it.copyToRealmOrUpdate(user)
            }
            realm.close()
            it.onSuccess(user)
        }
    }

    override fun removeUser(id: Int): Completable {
        return Completable.create {
            val realm = Realm.getDefaultInstance()
            val user = realm.where(User::class.java).equalTo("id", id).findFirst()
            realm.executeTransaction {
                user?.deleteFromRealm()
            }
            realm.close()
            it.onComplete()
        }
    }

    override fun removeAllUsers(): Completable {
        return Completable.create {
            val realm = Realm.getDefaultInstance()
            val users = realm.where(User::class.java).findAll()
            realm.executeTransaction {
                users.deleteAllFromRealm()
            }
            realm.close()
            it.onComplete()
        }
    }
}