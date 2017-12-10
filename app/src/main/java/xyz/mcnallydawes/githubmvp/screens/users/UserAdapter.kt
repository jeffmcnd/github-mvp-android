package xyz.mcnallydawes.githubmvp.screens.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.User

class UserAdapter(
        private val users : ArrayList<User>,
        private val listener: UserClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as UserViewHolder
        vh.bind(users[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
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

    fun removeAllUsers() {
        users.clear()
        notifyDataSetChanged()
    }

}