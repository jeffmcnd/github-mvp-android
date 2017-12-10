package xyz.mcnallydawes.githubmvp.screens.searchusers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_search_users.*
import xyz.mcnallydawes.githubmvp.Constants
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.di.NetInjection
import xyz.mcnallydawes.githubmvp.di.RepoInjection
import xyz.mcnallydawes.githubmvp.screens.repos.ReposActivity
import xyz.mcnallydawes.githubmvp.screens.users.UserAdapter
import xyz.mcnallydawes.githubmvp.screens.users.UserClickListener
import java.util.concurrent.TimeUnit

class SearchUsersActivity : AppCompatActivity(), SearchUsersContract.View {

    private lateinit var presenter : SearchUsersContract.Presenter
    private lateinit var adapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_users)

        val userRepo = RepoInjection.provideUserRepo(NetInjection.getGithubApi())
        SearchUsersPresenter(this, userRepo)
        presenter.initialize()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter.onBackBtnClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setPresenter(presenter: SearchUsersContract.Presenter) {
        this.presenter = presenter
    }

    override fun setUpSearchEditText() {
        searchEditText.setOnEditorActionListener { _, _, _ ->
            presenter.onSearchAction(searchEditText.text.toString(), null, null)
            true
        }

        RxTextView.afterTextChangeEvents(searchEditText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter.onSearchAction(searchEditText.text.toString(), null, null)
                }
    }

    override fun setUpClearBtn() = clearBtn.setOnClickListener { presenter.onClearBtnClicked() }

    override fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)
        adapter = UserAdapter(ArrayList(), object : UserClickListener {
            override fun onUserClicked(user: User) {
                presenter.onUserClicked(user)
            }
        })
        recyclerView.adapter = adapter
    }

    override fun hideEmptyView() {
        emptyView.visibility = View.GONE
    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        recyclerView.visibility = View.GONE
    }

    override fun showRecyclerView() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun clearSearchEditText() {
        searchEditText.setText("", TextView.BufferType.EDITABLE)
    }

    override fun clearRecyclerView() = adapter.removeAllUsers()

    override fun updateUsers(users: ArrayList<User>) {
        adapter.removeAllUsers()
        adapter.addUsers(users)
    }

    override fun goBack() = finish()

    override fun goToRepo(userId: Int) {
        val intent = Intent(this, ReposActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_ID, userId)
        startActivity(intent)
    }

}
