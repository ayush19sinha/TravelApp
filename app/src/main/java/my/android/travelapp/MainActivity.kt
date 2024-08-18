package my.android.travelapp

import FavoritesViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.razorpay.PaymentResultListener
import my.android.travelapp.navigation.Navigation
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.theme.TravelAppTheme
import my.android.travelapp.viewModels.AuthViewModel
import my.android.travelapp.viewModels.PaymentViewModel
import my.android.travelapp.viewModels.ThemeViewModel

class MainActivity : ComponentActivity(), PaymentResultListener {

    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var themeViewModel: ThemeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        themeViewModel = ViewModelProvider(this)[ThemeViewModel::class.java]
        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]


        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.observeAsState(initial = false)

            TravelAppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val favoritesViewModel: FavoritesViewModel = viewModel()
                val paymentSuccess = paymentViewModel.paymentSuccess.observeAsState()

                if (paymentSuccess.value == true) {
                    navController.navigate(Routes.home) {
                        popUpTo(Routes.home) { inclusive = true }
                    }
                    paymentViewModel.resetPaymentSuccess()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->



                    Navigation(
                        navController = navController,
                        favoritesViewModel = favoritesViewModel,
                        authViewModel = authViewModel,
                        themeViewModel = themeViewModel
                    )
                }
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        paymentViewModel.onPaymentSuccess(razorpayPaymentId)
        Toast.makeText(this, "Booking successful", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(code: Int, response: String?) {
        paymentViewModel.onPaymentError(code, response)
        Toast.makeText(this, "Error in payment: $response", Toast.LENGTH_LONG).show()
    }
}