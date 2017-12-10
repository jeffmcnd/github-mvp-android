package xyz.mcnallydawes.githubmvp.screens.users

import xyz.mcnallydawes.githubmvp.data.model.User

interface UserClickListener {

    fun onUserClicked(user: User)

}