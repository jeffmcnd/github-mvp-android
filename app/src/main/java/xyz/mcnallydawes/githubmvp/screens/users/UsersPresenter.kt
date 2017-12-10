package xyz.mcnallydawes.githubmvp.screens.users

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRepository

class UsersPresenter(
        private val view: UsersContract.View,
        private val userRepo: UserRepository
) : UsersContract.Presenter {

    private val disposables = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun initialize(state: UsersViewState) {
        view.setupUserList()

        getAllUsersFromLastUserId(0)
                .subscribe({
                    view.addUsers(it)
                    if (state.scrollPosition >= 0) view.scrollToPosition(state.scrollPosition)
                }, {
                    view.showErrorMessage()
                })
                .addTo(disposables)
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

    override fun onNextPage(lastUserId: Int, isLoading: Boolean) {
        if (isLoading) return

        getAllUsersFromLastUserId(lastUserId)
                .subscribe({
                    view.addUsers(it)
                }, {
                    view.showErrorMessage()
                })
                .addTo(disposables)
    }

    override fun onRefreshList() {
        view.hideList()
        userRepo.removeAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.removeAllUsers()
                }, {
                    view.showErrorMessage()
                })
                .addTo(disposables)
    }

    private fun getAllUsersFromLastUserId(lastUserId: Int) : Observable<ArrayList<User>> {
        return userRepo.getAllUsers(lastUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.setLoading(true) }
                .doOnNext { view.setLoading(false) }
                .doOnError { view.setLoading(false) }
    }

}