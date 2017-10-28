package xyz.mcnallydawes.githubmvp.data.model.local

import com.google.gson.annotations.SerializedName

data class User(
        val id: Int,
        val url: String,
        @SerializedName("login") val username: String,
        @SerializedName("avatar_url") val avatarUrl: String
)