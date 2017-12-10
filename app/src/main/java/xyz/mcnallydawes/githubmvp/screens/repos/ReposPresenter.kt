package xyz.mcnallydawes.githubmvp.screens.repos

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import xyz.mcnallydawes.githubmvp.data.model.Repo
import xyz.mcnallydawes.githubmvp.data.repo.repo.RepoRepository
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRepository

class ReposPresenter(
        private val view : ReposContract.View,
        private val userRepo : UserRepository,
        private val repoRepo : RepoRepository
) : ReposContract.Presenter {

    private val disposables = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun initialize(userId : Int) {
        view.setupReposList()

        view.showUserProgressbar()
        userRepo.get(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.hideUserProgressbar() }
                .subscribe({
                    if (it.username != null) view.setTitle(it.username!!)
                    if (it.avatarUrl != null) view.setAvatarIv(it.avatarUrl!!)
                    if (it.name != null) view.setNameTv(it.name!!)
                    else view.setNameTv("No name found")
                    if (it.location != null) view.setLocationTv(it.location!!)
                    else view.setLocationTv("No location found")
                }, {
                    view.showErrorMessage(it.localizedMessage)
                }, {}).addTo(disposables)

        view.showReposProgressbar()
        repoRepo.getUserRepos(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.hideReposProgressbar() }
                .subscribe({
                    if (it.size > 0) view.setRepos(it)
                    else view.showEmptyReposView()
                }, {
                    view.showEmptyReposView()
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

    override fun onRepoClicked(repo: Repo) {
        if (repo.url != null) view.showRepoUrl(repo.url!!)
        else view.showErrorMessage("This repo has no URL")
    }

}