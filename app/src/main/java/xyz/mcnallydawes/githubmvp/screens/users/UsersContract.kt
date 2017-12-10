package xyz.mcnallydawes.githubmvp.screens.users

import xyz.mcnallydawes.githubmvp.data.model.User

interface UsersContract {

    interface View {
        fun setPresenter(presenter: UsersContract.Presenter)
        fun setupUserList()

        fun addUser(user: User)
        fun addUsers(users: ArrayList<User>)
        fun updateUser(user: User)
        fun removeUser(user: User)
        fun removeAllUsers()

        fun showErrorMessage()
        fun showUserView(user: User)

        fun scrollToPosition(position: Int)

        fun hideRefreshIndicator()

        fun showList()
        fun hideList()

        fun setLoading(value: Boolean)
        fun goToSearch()
    }

    interface Presenter {
        fun initialize(state : UsersViewState)
        fun terminate()
        fun onUserClicked(user: User)
        fun onUserRemoved(user: User)
        fun onNextPage(lastUserId: Int, isLoading: Boolean)
        fun onRefreshList()
        fun onSearchBtnClicked()
    }

}