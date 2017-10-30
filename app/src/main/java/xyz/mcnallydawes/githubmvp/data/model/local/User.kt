package xyz.mcnallydawes.githubmvp.data.model.local

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class User @JvmOverloads constructor(
        @PrimaryKey var id: Int = Random().nextInt(),
        var url: String = "url",
        @SerializedName("login") var username: String = "username",
        @SerializedName("avatar_url") var avatarUrl: String = "avatarUrl"
) : RealmObject()