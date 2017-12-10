package xyz.mcnallydawes.githubmvp.screens.searchusers

import xyz.mcnallydawes.githubmvp.data.model.User

interface SearchUsersContract {

    interface Presenter {
        fun initialize()
        fun terminate()
        fun onBackBtnClicked()
        fun onClearBtnClicked()
        fun onSearchAction(q: String, sort: String?, order: String?)
        fun onUserClicked(user: User)
    }

    interface View {
        fun setPresenter(presenter: Presenter)
        fun setUpSearchEditText()
        fun setUpClearBtn()
        fun setUpRecyclerView()

        fun hideEmptyView()
        fun showEmptyView()

        fun hideRecyclerView()
        fun showRecyclerView()

        fun clearSearchEditText()
        fun clearRecyclerView()
        fun updateUsers(users: ArrayList<User>)

        fun goBack()
        fun goToRepo(userId: Int)
    }

}