package xyz.mcnallydawes.githubmvp.userdetail

interface UserDetailContract {

    interface View {

        fun setPresenter(presenter : Presenter)

        fun setAvatarIv(url : String)

        fun setNameTv(name : String)

        fun setUsernameTv(username : String)

        fun setLocationTv(location : String)

        fun showPreviousView()

        fun showUserNotFoundError()

        fun showErrorMessage(message : String)

    }

    interface Presenter {

        fun initialize(userId : Int)

        fun terminate()

        fun onBackBtnClicked()

    }

}