package xyz.mcnallydawes.githubmvp.screens.users

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import kotlinx.android.synthetic.main.activity_users.*
import xyz.mcnallydawes.githubmvp.Constants
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.di.NetInjection
import xyz.mcnallydawes.githubmvp.di.RepoInjection
import xyz.mcnallydawes.githubmvp.screens.repos.ReposActivity
import xyz.mcnallydawes.githubmvp.screens.searchusers.SearchUsersActivity
import java.util.concurrent.TimeUnit

class UsersActivity : AppCompatActivity(), UsersContract.View {

    private val VISIBLE_THRESHOLD = 3

    private var adapter : UserAdapter? = null
    private lateinit var presenter: UsersContract.Presenter
    private lateinit var state : UsersViewState

    override fun setPresenter(presenter: UsersContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val userRepo = RepoInjection.provideUserRepo(NetInjection.getGithubApi())
        UsersPresenter(this, userRepo)

        setSupportActionBar(toolbar)

        state = if (savedInstanceState != null && savedInstanceState.containsKey(UsersViewState.KEY)) {
            savedInstanceState[UsersViewState.KEY] as UsersViewState
        } else {
            UsersViewState()
        }
        presenter.initialize(state)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
        state.scrollPosition = layoutManager.findFirstVisibleItemPosition()
        outState?.putParcelable(UsersViewState.KEY, state)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_users, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSearch -> {
                presenter.onSearchBtnClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.terminate()
    }

    override fun setupUserList() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.onRefreshList()
        }

        recyclerView.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)
        adapter = UserAdapter(ArrayList(), object : UserClickListener {
            override fun onUserClicked(user: User) {
                presenter.onUserClicked(user)
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
                    presenter.onNextPage(state.lastUserId, state.isLoading)
                }
    }

    override fun addUser(user: User) {
        adapter?.addUser(user)
    }

    override fun addUsers(users: ArrayList<User>) {
        adapter?.addUsers(users)
        state.lastUserId = users.last().id
    }

    override fun updateUser(user: User) {
        adapter?.updateUser(user)
    }

    override fun removeUser(user: User) {
        adapter?.removeUser(user)
    }

    override fun removeAllUsers() {
        adapter?.removeAllUsers()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show()
    }

    override fun showUserView(user: User) {
        val intent = Intent(this, ReposActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_ID, user.id)
        startActivity(intent)
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

    override fun hideRefreshIndicator() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showList() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideList() {
        recyclerView.visibility = View.INVISIBLE
    }

    override fun setLoading(value: Boolean) {
        state.isLoading = value
    }

    override fun goToSearch() {
        startActivity(Intent(this, SearchUsersActivity::class.java))
    }

}
