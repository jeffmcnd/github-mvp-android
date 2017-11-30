package xyz.mcnallydawes.githubmvp.repos

import xyz.mcnallydawes.githubmvp.data.model.local.Repo

interface RepoClickListener {

    fun onRepoClicked(repo : Repo)

}