package xyz.mcnallydawes.githubmvp.users

import xyz.mcnallydawes.githubmvp.data.model.local.User

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

    }

    interface Presenter {

        fun initialize(savedScrollPosition: Int)

        fun terminate()

        fun onUserClicked(user: User)

        fun onUserRemoved(user: User)

        fun onNextPage()

        fun onRefreshList()

    }

}