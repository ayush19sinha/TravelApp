package my.android.travelapp.data

import androidx.annotation.StringRes
import my.android.travelapp.R

data class DetailData(
    val img: Int,
    @StringRes val title: Int,
    val rating: String,
    val ratingCount: String,
    @StringRes val desc: Int,
    @StringRes val location: Int,
    val price : Int,
    val continent: String
)

val details = listOf(
    DetailData(R.drawable.mount_fuji, R.string.mount_fuji, "5","(2736)", R.string.mount_fuji_desc, R.string.mount_fuji_location, 1000, "Asia"),
    DetailData(R.drawable.mount_everest, R.string.everest, "5","(9789)", R.string.everest_desc, R.string.everest_location, 1000, "Asia"),
    DetailData(R.drawable.kailash, R.string.kailash, "5","(6245)", R.string.kailash_desc, R.string.kailash_location, 1200, "Asia"),
    DetailData(R.drawable.dolomite, R.string.dolomites, "4.9","(1231)", R.string.dolomites_desc, R.string.dolomites_location, 1080, "Europe"),
    DetailData(R.drawable.denali, R.string.denali,"4.5","(3341)", R.string.denali_desc, R.string.denali_location, 1060, "North America"),
    DetailData(R.drawable.mount_matterhorn, R.string.matterhorn_mountain, "4.8","(1220)", R.string.matterhorn_desc, R.string.matterhorn_location, 1000, "Europe"),
    DetailData(R.drawable.alps, R.string.alps,"5","(1235)", R.string.alps_desc, R.string.alps_location, 1100, "Europe"),
    DetailData(R.drawable.mount_olympus, R.string.mount_olympus, "5","(1567)", R.string.mount_olympus_desc, R.string.mount_olympus_location, 1000, "Europe"),
    DetailData(R.drawable.mount_kilimanjaro, R.string.mount_kilimanjaro, "4.6","(4002)", R.string.mount_kilimanjaro_desc, R.string.mount_kilimanjaro_location, 1140, "Africa"),
    DetailData(R.drawable.ananthagiri, R.string.ananthagiri_hills, "5","(1768)", R.string.ananthagiri_desc, R.string.ananthagiri_location, 1250,"Asia"),
    DetailData(R.drawable.amazon_river, R.string.amazon_river, "4.2","(1367)", R.string.amazon_river_desc, R.string.amazon_river_location, 1160, "Europe"),
    DetailData(R.drawable.mirik, R.string.mirik,"3.8","(2467)", R.string.mirik_desc, R.string.mirik_location, 1100, "Asia"),
    )



