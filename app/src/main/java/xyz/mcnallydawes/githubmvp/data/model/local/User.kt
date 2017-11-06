package xyz.mcnallydawes.githubmvp.data.model.local

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class User @JvmOverloads constructor(
        @PrimaryKey @SerializedName("id") var id: Int = Random().nextInt(),
        @SerializedName("avatar_url") var avatarUrl: String? = null,
        @SerializedName("location") var location : String? = null,
        @SerializedName("login") var username: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("url") var url: String? = null
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return other.id == id &&
                    other.avatarUrl == avatarUrl &&
                    other.location == location &&
                    other.username == username &&
                    other.name == name &&
                    other.url == url
        }
        return super.equals(other)
    }

}