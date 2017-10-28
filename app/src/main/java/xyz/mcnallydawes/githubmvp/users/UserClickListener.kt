package xyz.mcnallydawes.githubmvp.users

import xyz.mcnallydawes.githubmvp.data.model.local.User

interface UserClickListener {

    fun onUserClicked(user: User)

}