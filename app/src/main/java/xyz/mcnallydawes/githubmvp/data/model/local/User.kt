package xyz.mcnallydawes.githubmvp.data.model.local

import com.google.gson.annotations.SerializedName

data class User(
        var id: Int,
        var url: String,
        @SerializedName("login") var username: String,
        @SerializedName("avatar_url") var avatarUrl: String
)