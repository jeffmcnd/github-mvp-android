package xyz.mcnallydawes.githubmvp.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.local.User

class UserAdapter(
        private val users : ArrayList<User>,
        private val listener: UserClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_VIEW = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == users.size) FOOTER_VIEW else super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            FOOTER_VIEW -> {
                (holder as UserLoadingViewHolder).bind()
            }
            else -> {
                val vh = holder as UserViewHolder
                vh.bind(users[position], listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val context = parent?.context
        return when (viewType) {
            FOOTER_VIEW -> {
                val view = LayoutInflater.from(context)
                        .inflate(R.layout.item_user_loading, parent, false)
                UserLoadingViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context)
                        .inflate(R.layout.item_user, parent, false)
                UserViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size + 1
    }

    fun getUser(position: Int) : User {
        return users[position]
    }

    fun addUser(user: User) {
        users.add(user)
        notifyItemInserted(itemCount - 1)
    }

    fun addUsers(users: ArrayList<User>) {
        val startPos = itemCount
        this.users.addAll(users)
        notifyItemRangeInserted(startPos, itemCount - 1)
    }

    fun updateUser(user: User) {
        val index = users.indexOf(user)
        if (index > 0) {
            users[index] = user
            notifyItemChanged(index)
        }
    }

    fun removeUser(user: User) {
        val index = users.indexOf(user)
        if (users.remove(user)) {
            notifyItemRemoved(index)
        }
    }

}