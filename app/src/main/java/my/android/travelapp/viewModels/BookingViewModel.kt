import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.android.travelapp.data.Booking

class BookingViewModel : ViewModel() {
    private val _bookings = MutableLiveData<List<Booking>>(emptyList())
    val bookings: LiveData<List<Booking>> = _bookings

    fun addTripToBookings(booking: Booking) {
        val currentList = _bookings.value ?: emptyList()
        _bookings.value = currentList + booking
    }
}