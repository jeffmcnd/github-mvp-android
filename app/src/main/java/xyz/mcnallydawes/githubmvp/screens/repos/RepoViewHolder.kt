package xyz.mcnallydawes.githubmvp.screens.repos

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_repo.view.*
import xyz.mcnallydawes.githubmvp.data.model.Repo

class RepoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    fun bind(repo: Repo, listener: RepoClickListener) = with(itemView) {
        nameTv.text = repo.name
        stargazersCountTv.text = repo.stargazersCount.toString()
        watchersCountTv.text = repo.watchersCount.toString()

        setOnClickListener { listener.onRepoClicked(repo) }
    }

}
