package xyz.mcnallydawes.githubmvp.screens.repos

import xyz.mcnallydawes.githubmvp.data.model.Repo

interface RepoClickListener {

    fun onRepoClicked(repo : Repo)

}