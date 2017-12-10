package xyz.mcnallydawes.githubmvp.screens.repos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_repos.*
import xyz.mcnallydawes.githubmvp.Constants
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.Repo
import xyz.mcnallydawes.githubmvp.di.NetInjection
import xyz.mcnallydawes.githubmvp.di.RepoInjection
import xyz.mcnallydawes.githubmvp.repodetail.RepoAdapter
import xyz.mcnallydawes.githubmvp.utils.CircleTransform

class ReposActivity : AppCompatActivity(), ReposContract.View {

    private lateinit var presenter : ReposContract.Presenter
    private var adapter : RepoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)

        val userId = intent.getIntExtra(Constants.EXTRA_USER_ID, -1)

        val githubApi = NetInjection.getGithubApi()
        val userRepo = RepoInjection.provideUserRepo(githubApi)
        val repoRepo = RepoInjection.provideRepoRepo(githubApi)
        ReposPresenter(this, userRepo, repoRepo)
        presenter.initialize(userId)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setPresenter(presenter: ReposContract.Presenter) {
        this.presenter = presenter
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

    override fun onDestroy() {
        super.onDestroy()
        presenter?.terminate()
    }

    override fun setupReposList() {
        reposRv.layoutManager =
                object : LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
                    override fun canScrollVertically(): Boolean { return false }
                }
        adapter = RepoAdapter(ArrayList(), object : RepoClickListener {
            override fun onRepoClicked(repo: Repo) {
                presenter?.onRepoClicked(repo)
            }
        })
        reposRv.adapter = adapter
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setAvatarIv(url: String) {
        Picasso.with(this)
                .load(url)
                .transform(CircleTransform())
                .error(R.drawable.octocat)
                .into(avatarIv)
    }

    override fun setNameTv(name: String) {
        nameTv.text = name
    }

    override fun setLocationTv(location: String) {
        locationTv.text = location
    }

    override fun showPreviousView() {
        finish()
    }

    override fun showUserNotFoundError() {
        Toast.makeText(this, "Unable to retrieve user", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showUserProgressbar() {
        userProgressBar.visibility = View.VISIBLE
    }

    override fun hideUserProgressbar() {
        userProgressBar.visibility = View.GONE
    }

    override fun showReposProgressbar() {
        reposProgressBar.visibility = View.VISIBLE
    }

    override fun hideReposProgressbar() {
        reposProgressBar.visibility = View.GONE
    }

    override fun setRepos(repos: ArrayList<Repo>) {
        adapter?.replaceRepos(repos)
    }

    override fun showEmptyReposView() {
//        TODO: show empty view
    }

    override fun showRepoUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}