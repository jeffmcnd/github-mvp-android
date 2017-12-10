package xyz.mcnallydawes.githubmvp.screens.users

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.utils.CircleTransform

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User, listener: UserClickListener) = with(itemView) {
        Picasso.with(avatarIv.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.octocat)
                .transform(CircleTransform())
                .into(avatarIv)

        usernameTv.text = user.username

        setOnClickListener { listener.onUserClicked(user) }
    }

}