package my.android.travelapp.data

import androidx.annotation.StringRes

data class Booking(
    val img: Int,
    @StringRes val title: Int,
    val rating: String,
    val ratingCount: String,
    @StringRes val desc: Int,
    @StringRes val location: Int,
    val price : Int,
    val continent: String,
    val date: Long
)