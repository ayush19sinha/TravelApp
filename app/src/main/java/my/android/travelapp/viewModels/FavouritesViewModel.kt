import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import my.android.travelapp.data.DetailData

class FavoritesViewModel : ViewModel() {
    private val _favoritePlaces = MutableStateFlow<Set<DetailData>>(emptySet())
    val favoritePlaces: StateFlow<Set<DetailData>> = _favoritePlaces

    fun toggleFavorite(place: DetailData) {
        viewModelScope.launch {
            val currentFavorites = _favoritePlaces.value.toMutableSet()
            if (currentFavorites.contains(place)) {
                currentFavorites.remove(place)
            } else {
                currentFavorites.add(place)
            }
            _favoritePlaces.value = currentFavorites
        }
    }

    fun isFavorite(place: DetailData): Flow<Boolean> {
        return _favoritePlaces.map { it.contains(place) }
    }
}