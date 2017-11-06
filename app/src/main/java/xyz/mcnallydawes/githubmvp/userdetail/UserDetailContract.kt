package xyz.mcnallydawes.githubmvp.userdetail

interface UserDetailContract {

    interface View {

        fun setPresenter(presenter : Presenter)

        fun setTitle(title : String)

        fun setAvatarIv(url : String)

        fun setNameTv(name : String)

        fun setLocationTv(location : String)

        fun showPreviousView()

        fun showUserNotFoundError()

        fun showErrorMessage(message : String)

        fun showProgressbar()

        fun hideProgressbar()

    }

    interface Presenter {

        fun initialize(userId : Int)

        fun terminate()

        fun onBackBtnClicked()

    }

}