package xyz.mcnallydawes.githubmvp.users

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_users.*
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.local.User

class UsersFragment: Fragment(), UsersContract.View {

    private val VISIBLE_THRESHOLD = 5

    private var presenter: UsersContract.Presenter? = null

    private var adapter : UserAdapter? = null

    companion object {
        fun newInstance(): UsersFragment {
            return UsersFragment()
        }
    }

    override fun setPresenter(presenter: UsersContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.initialize()
    }

    override fun setupUserList() {
        recyclerView.layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL, false)
        adapter = UserAdapter(ArrayList(), object : UserClickListener {
            override fun onUserClicked(user: User) {
                presenter?.onUserClicked(user)
            }
        })
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    presenter?.onNextPage()
                }
            }
        })
    }

    override fun addUser(user: User) {
        adapter?.addUser(user)
    }

    override fun addUsers(users: ArrayList<User>) {
        adapter?.addUsers(users)
    }

    override fun updateUser(user: User) {
        adapter?.updateUser(user)
    }

    override fun removeUser(user: User) {
        adapter?.removeUser(user)
    }

    override fun showErrorMessage() {
        Toast.makeText(activity, "An error occurred.", Toast.LENGTH_SHORT).show()
    }

    override fun showUserView(user: User) {
        Toast.makeText(activity, "Tapped " + user.username + ".", Toast.LENGTH_SHORT).show()
    }

}