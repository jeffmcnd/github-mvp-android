package xyz.mcnallydawes.githubmvp.userdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.di.RepoInjection
import xyz.mcnallydawes.githubmvp.utils.InjectionUtils

class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val fragment = fragmentManager.findFragmentById(R.id.fragment)
                as UserDetailFragment? ?: UserDetailFragment.newInstance().also {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.fragment, it)
            transaction.commit()
        }

        val userRepo = RepoInjection.provideUserRepo(InjectionUtils.getGithubApi())
        UserDetailPresenter(fragment, userRepo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}