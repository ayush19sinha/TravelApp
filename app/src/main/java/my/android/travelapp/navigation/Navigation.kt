package my.android.travelapp.navigation

import FavoritesViewModel
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import my.android.travelapp.data.details
import my.android.travelapp.ui.details.DetailsPage
import my.android.travelapp.ui.home.Home
import my.android.travelapp.ui.home.MostVisited
import my.android.travelapp.ui.home.RecommendedPage
import my.android.travelapp.ui.home.SearchPage
import my.android.travelapp.ui.onboarding.ForgetPassword
import my.android.travelapp.ui.onboarding.LoginScreen
import my.android.travelapp.ui.onboarding.OnboardingScreen
import my.android.travelapp.ui.onboarding.OtpVerification
import my.android.travelapp.ui.onboarding.SignupScreen
import my.android.travelapp.ui.profile.AboutDeveloperPage
import my.android.travelapp.ui.profile.BookingPage
import my.android.travelapp.ui.profile.DeletionScreen
import my.android.travelapp.ui.profile.EditProfile
import my.android.travelapp.ui.profile.FavouriteScreen
import my.android.travelapp.ui.profile.Profile
import my.android.travelapp.ui.profile.SettingPage
import my.android.travelapp.viewModels.AuthViewModel
import my.android.travelapp.viewModels.ThemeViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation(navController: NavController,
               favoritesViewModel: FavoritesViewModel,
               authViewModel: AuthViewModel,
               themeViewModel: ThemeViewModel){

    SharedTransitionLayout {

        NavHost(navController = navController as NavHostController,
            startDestination = Routes.onboardingScreen) {

            composable(Routes.onboardingScreen){
                OnboardingScreen(navController)
            }
            composable(Routes.login){
                LoginScreen(navController, authViewModel)
            }
            composable(Routes.signup){
                SignupScreen(navController, authViewModel)
            }
            composable(Routes.forgetPassword){
                ForgetPassword(navController)
            }
            composable(Routes.otpVerification){
                OtpVerification(navController)
            }

            composable(Routes.home){
                Home(navController, favoritesViewModel, authViewModel, themeViewModel)
            }

            composable(
                route = Routes.detail,
                arguments = listOf(navArgument("placeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
                DetailsPage(navController, detailData = details[placeId],
                    viewModel = favoritesViewModel,
                )
            }


            composable(Routes.mybooking){
                BookingPage(navController = navController)
            }

            composable(Routes.recommended){
                RecommendedPage(navController, favoritesViewModel)
            }
            composable(Routes.profile){
                Profile(navController, authViewModel)
            }
            composable(Routes.setting){
                SettingPage(navController)
            }
            composable(Routes.aboutDeveloper){
                AboutDeveloperPage(navController)
            }
            composable(Routes.editprofile){
                EditProfile(navController, authViewModel)
            }
            composable(Routes.search){
                SearchPage(navController, favoritesViewModel)
            }

            composable(Routes.mostvisited){
                MostVisited(navController, favoritesViewModel)
            }
            composable(Routes.favourite){
                FavouriteScreen(navController, favoritesViewModel)
            }
            composable(Routes.confirmDeletion){
                DeletionScreen(navController, authViewModel)
            }
        }

    }

}