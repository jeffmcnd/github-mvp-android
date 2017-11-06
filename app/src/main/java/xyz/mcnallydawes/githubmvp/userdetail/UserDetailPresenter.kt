package xyz.mcnallydawes.githubmvp.userdetail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository

class UserDetailPresenter(
        private val view : UserDetailContract.View,
        private val userRepo: UserRepository
) : UserDetailContract.Presenter {

    private val disposables = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun initialize(userId : Int) {
        view.showProgressbar()
        userRepo.get(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.hideProgressbar() }
                .subscribe({
                    if (it.username != null) view.setTitle(it.username!!)
                    if (it.avatarUrl != null) view.setAvatarIv(it.avatarUrl!!)
                    if (it.name != null) view.setNameTv(it.name!!)
                    if (it.location != null) view.setLocationTv(it.location!!)
                }, {
                    view.showErrorMessage(it.localizedMessage)
                }, {}).addTo(disposables)
    }

    override fun terminate() {
        disposables.clear()
        disposables.dispose()
    }

    override fun onBackBtnClicked() {
        view.showPreviousView()
    }

}