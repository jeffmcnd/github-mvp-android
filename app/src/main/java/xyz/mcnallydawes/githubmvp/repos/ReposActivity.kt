package xyz.mcnallydawes.githubmvp.repos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.di.RepoInjection
import xyz.mcnallydawes.githubmvp.utils.InjectionUtils

class ReposActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)

        val fragment = fragmentManager.findFragmentById(R.id.fragment)
                as ReposFragment? ?: ReposFragment.newInstance().also {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.fragment, it)
            transaction.commit()
        }

        val githubApi = InjectionUtils.getGithubApi()
        val userRepo = RepoInjection.provideUserRepo(githubApi)
        val repoRepo = RepoInjection.provideRepoRepo(githubApi)
        ReposPresenter(fragment, userRepo, repoRepo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}