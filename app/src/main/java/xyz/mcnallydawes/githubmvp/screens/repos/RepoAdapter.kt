package xyz.mcnallydawes.githubmvp.repodetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.mcnallydawes.githubmvp.R
import xyz.mcnallydawes.githubmvp.data.model.Repo
import xyz.mcnallydawes.githubmvp.screens.repos.RepoClickListener
import xyz.mcnallydawes.githubmvp.screens.repos.RepoViewHolder

class RepoAdapter(
        private val repos : ArrayList<Repo>,
        private val listener: RepoClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_VIEW = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == repos.size) FOOTER_VIEW else super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as RepoViewHolder
        return vh.bind(repos[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun getRepo(position: Int) : Repo {
        return repos[position]
    }

    fun addRepo(repo: Repo) {
        repos.add(repo)
        notifyItemInserted(itemCount - 1)
    }

    fun addRepos(repos: ArrayList<Repo>) {
        val startPos = itemCount
        this.repos.addAll(repos)
        notifyItemRangeInserted(startPos, itemCount - 1)
    }

    fun updateRepo(repo: Repo) {
        val index = repos.indexOf(repo)
        if (index > 0) {
            repos[index] = repo
            notifyItemChanged(index)
        }
    }

    fun removeRepo(repo: Repo) {
        val index = repos.indexOf(repo)
        if (repos.remove(repo)) {
            notifyItemRemoved(index)
        }
    }

    fun removeAllRepos() {
        repos.clear()
        notifyDataSetChanged()
    }

    fun replaceRepos(repos : ArrayList<Repo>) {
        this.repos.clear()
        this.repos += repos
        notifyDataSetChanged()
    }

}
