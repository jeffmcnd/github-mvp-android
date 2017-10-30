package xyz.mcnallydawes.githubmvp.users

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import xyz.mcnallydawes.githubmvp.data.model.local.User
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository

class UsersPresenter(
        private val view: UsersContract.View,
        private val userRepo: UserRepository
) : UsersContract.Presenter {

    private var lastUserId = 0
    private var loading = false
    private val disposables = CompositeDisposable()

    override fun initialize() {
        view.setupUserList()
    }

    override fun terminate() {
        disposables.clear()
        disposables.dispose()
    }

    override fun onUserClicked(user: User) {
        view.showUserView(user)
    }

    override fun onUserRemoved(user: User) {
        view.removeUser(user)
    }

    override fun onNextPage() {
        if (loading) {
            return
        }

        loading = true
        userRepo.getUsers(lastUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lastUserId = it.last().id
                    view.addUsers(it)
                    loading = false
                }, {
                    view.showErrorMessage()
                    loading = false
                })
                .addTo(disposables)
    }

}