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

        fun showErrorMessage()

        fun showUserView(user: User)

    }

    interface Presenter {

        fun initialize()

        fun terminate()

        fun onUserClicked(user: User)

        fun onUserRemoved(user: User)

        fun onNextPage()

    }

}