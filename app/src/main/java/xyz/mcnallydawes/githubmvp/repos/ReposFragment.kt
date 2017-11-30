package xyz.mcnallydawes.githubmvp.repos

import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repos.*
import xyz.mcnallydawes.githubmvp.Constants
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.local.Repo
import xyz.mcnallydawes.githubmvp.repodetail.RepoAdapter
import xyz.mcnallydawes.githubmvp.utils.CircleTransform

class ReposFragment : Fragment(), ReposContract.View {

    private var presenter : ReposContract.Presenter? = null

    private var adapter : RepoAdapter? = null

    companion object {
        fun newInstance(): ReposFragment {
            return ReposFragment()
        }
    }

    override fun setPresenter(presenter: ReposContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = activity.intent.getIntExtra(Constants.EXTRA_USER_ID, -1)
        presenter?.initialize(userId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter?.onBackBtnClicked()
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
                object : LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) {
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
        (activity as ReposActivity).supportActionBar?.title = title
    }

    override fun setAvatarIv(url: String) {
        Picasso.with(activity)
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
        activity.finish()
    }

    override fun showUserNotFoundError() {
        Toast.makeText(activity, "Unable to retrieve user", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message : String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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