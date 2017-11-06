package xyz.mcnallydawes.githubmvp.userdetail

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_detail.*
import xyz.mcnallydawes.githubmvp.Constants
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.utils.CircleTransform

class UserDetailFragment : Fragment(), UserDetailContract.View {

    private var presenter : UserDetailContract.Presenter? = null

    companion object {
        fun newInstance(): UserDetailFragment {
            return UserDetailFragment()
        }
    }

    override fun setPresenter(presenter: UserDetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_user_detail, container, false)
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

    override fun setTitle(title: String) {
        (activity as UserDetailActivity).supportActionBar?.title = title
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

    override fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = View.GONE
    }
}