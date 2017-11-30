package xyz.mcnallydawes.githubmvp.data.model.local

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Repo(
        @PrimaryKey @SerializedName("id") var id: Int = Random().nextInt(),
        @SerializedName("html_url") var url : String? = null,
        @SerializedName("name") var name : String? = null,
        @SerializedName("stargazers_count") var stargazersCount : Int = 0,
        @SerializedName("updated_at") var updatedAt : String? = null,
        @SerializedName("user_id") var userId : Int = 0,
        @SerializedName("watchers_count") var watchersCount : Int = 0
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (other is Repo) {
            return other.id == id &&
                    other.url == url &&
                    other.name == name &&
                    other.stargazersCount == stargazersCount &&
                    other.updatedAt == updatedAt &&
                    other.userId == userId &&
                    other.watchersCount == watchersCount
        }
        return super.equals(other)
    }

}