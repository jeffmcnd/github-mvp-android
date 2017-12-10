package xyz.mcnallydawes.githubmvp.screens.repos

import xyz.mcnallydawes.githubmvp.data.model.Repo

interface ReposContract {

    interface View {

        fun setPresenter(presenter : Presenter)

        fun setupReposList()

        fun setTitle(title : String)

        fun setAvatarIv(url : String)

        fun setNameTv(name : String)

        fun setLocationTv(location : String)

        fun showPreviousView()

        fun showUserNotFoundError()

        fun showErrorMessage(message : String)

        fun showUserProgressbar()

        fun hideUserProgressbar()

        fun setRepos(repos : ArrayList<Repo>)

        fun showEmptyReposView()

        fun showRepoUrl(url : String)

        fun showReposProgressbar()

        fun hideReposProgressbar()

    }

    interface Presenter {

        fun initialize(userId : Int)

        fun terminate()

        fun onBackBtnClicked()

        fun onRepoClicked(repo : Repo)

    }

}