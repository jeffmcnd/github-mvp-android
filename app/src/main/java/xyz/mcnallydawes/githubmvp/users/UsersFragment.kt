package xyz.mcnallydawes.githubmvp.users

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import kotlinx.android.synthetic.main.fragment_users.*
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.local.User
import java.util.concurrent.TimeUnit

class UsersFragment: Fragment(), UsersContract.View {

    private val VISIBLE_THRESHOLD = 3

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
        val position = savedInstanceState?.getInt("recyclerViewPosition") ?: -1
        presenter?.initialize(position)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
        val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        outState?.putInt("recyclerViewPosition", firstVisibleItemPosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.terminate()
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

        RxRecyclerView.scrollEvents(recyclerView)
                .filter {
                    val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD
                }
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter?.onNextPage()
                }
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

    override fun scrollToPosition(position: Int) {
        recyclerView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(recyclerView.height, View.MeasureSpec.EXACTLY)
        )

        val itemView = recyclerView.getChildAt(0)
        itemView.measure(
                View.MeasureSpec.makeMeasureSpec(itemView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(itemView.height, View.MeasureSpec.EXACTLY)
        )

        val numVisibleItems = recyclerView.height / itemView.height
        val indexOffset =  numVisibleItems - 1
        val completelyVisibleItemsHeight = numVisibleItems * itemView.measuredHeight
        val pixelOffset = Math.abs(completelyVisibleItemsHeight - recyclerView.measuredHeight)

        val index = position + indexOffset
        recyclerView.scrollToPosition(index)
        recyclerView.scrollBy(0, pixelOffset)
    }

}