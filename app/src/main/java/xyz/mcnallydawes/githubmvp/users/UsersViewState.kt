package xyz.mcnallydawes.githubmvp.users

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class UsersViewState(
        var lastUserId : Int = 0,
        var scrollPosition : Int = -1,
        var isLoading : Boolean = false
) : Parcelable {
    companion object {
        val KEY = "UsersViewState"
    }
}
