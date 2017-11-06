package xyz.mcnallydawes.githubmvp.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.di.RepoInjection
import xyz.mcnallydawes.githubmvp.utils.InjectionUtils

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = fragmentManager.findFragmentById(R.id.fragment)
        as UsersFragment? ?: UsersFragment.newInstance().also {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.fragment, it)
            transaction.commit()
        }

        val userRepo = RepoInjection.provideUserRepo(InjectionUtils.getGithubApi())
        UsersPresenter(fragment, userRepo)

        supportActionBar?.title = "Github Users"
    }

}
