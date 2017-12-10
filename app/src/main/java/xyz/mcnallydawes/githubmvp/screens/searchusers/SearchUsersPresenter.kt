package xyz.mcnallydawes.githubmvp.screens.searchusers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRepository

class SearchUsersPresenter(
        private val view: SearchUsersContract.View,
        private val userRepo: UserRepository
) : SearchUsersContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun initialize() {
        view.setUpRecyclerView()
        view.setUpSearchEditText()
        view.setUpClearBtn()
        view.hideRecyclerView()
        view.showEmptyView()
    }

    override fun terminate() {
    }

    override fun onBackBtnClicked() {
        view.goBack()
    }

    override fun onClearBtnClicked() {
        view.clearSearchEditText()
        view.clearRecyclerView()
        view.hideRecyclerView()
        view.showEmptyView()
    }

    override fun onSearchAction(q: String, sort: String?, order: String?) {
        if (q == "") return

        userRepo.searchUsers(q, sort, order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        view.clearRecyclerView()
                        view.showEmptyView()
                        view.hideRecyclerView()
                    } else {
                        view.updateUsers(it)
                        view.hideEmptyView()
                        view.showRecyclerView()
                    }
                }, {
                    view.clearRecyclerView()
                    view.hideRecyclerView()
                    view.showEmptyView()
                })
    }

    override fun onUserClicked(user: User) {
        view.goToRepo(user.id)
    }

}