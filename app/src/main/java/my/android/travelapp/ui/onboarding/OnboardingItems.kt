package my.android.travelapp.ui.onboarding

import androidx.annotation.StringRes
import my.android.travelapp.R

data class OnboardingItems(
    val img: Int,
    @StringRes val title : Int,
    @StringRes val desc : Int
){
companion object {
fun get() = listOf(
    OnboardingItems(R.drawable.onboarding_1, R.string.onboarding_title_1, R.string.onboarding_desc_1),
    OnboardingItems(R.drawable.onboarding_2, R.string.onboarding_title_2, R.string.onboarding_desc_2),
    OnboardingItems(R.drawable.onboarding_3, R.string.onboarding_title_3, R.string.onboarding_desc_3),
        )
    }
}