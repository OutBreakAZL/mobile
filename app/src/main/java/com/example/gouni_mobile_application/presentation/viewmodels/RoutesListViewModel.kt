package com.example.gouni_mobile_application.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gouni_mobile_application.domain.models.Route
import com.example.gouni_mobile_application.domain.repository.RouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

class RoutesListViewModel @Inject constructor(
    private val routeRepository: RouteRepository
) : ViewModel() {

    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadRoutes()
    }

    fun loadRoutes() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val routesList = routeRepository.getRoutes()
                _routes.value = routesList
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar las rutas: ${e.message}"
                // Si hay error, cargar datos de ejemplo para preview
                _routes.value = getExampleRoutes()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getExampleRoutes(): List<Route> {
        return listOf(
            Route(
                start = "San Miguel - UPC",
                end = "Miraflores",
                days = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                departureTime = LocalTime.of(15, 30),
                arrivalTime = LocalTime.of(16, 30)
            ),
            Route(
                start = "USIL",
                end = "Magdalena",
                days = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                departureTime = LocalTime.of(15, 30),
                arrivalTime = LocalTime.of(16, 45)
            ),
            Route(
                start = "San Isidro - UPC",
                end = "San Miguel",
                days = listOf(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                departureTime = LocalTime.of(15, 30),
                arrivalTime = LocalTime.of(17, 0)
            )
        )
    }

    fun refreshRoutes() {
        loadRoutes()
    }
}

// Constructor sin inyección para casos donde no se use Hilt
class RoutesListViewModelNoHilt : ViewModel() {

    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadRoutes()
    }

    fun loadRoutes() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Simular carga de datos
                kotlinx.coroutines.delay(1000)
                _routes.value = getExampleRoutes()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar las rutas: ${e.message}"
                _routes.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getExampleRoutes(): List<Route> {
        return listOf(
            Route(
                start = "San Miguel - UPC",
                end = "Miraflores",
                days = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                departureTime = LocalTime.of(15, 30),
                arrivalTime = LocalTime.of(16, 30)
            ),
            Route(
                start = "USIL",
                end = "Magdalena",
                days = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                departureTime = LocalTime.of(15, 30),
                arrivalTime = LocalTime.of(16, 45)
            ),
            Route(
                start = "San Isidro - UPC",
                end = "San Miguel",
                days = listOf(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                departureTime = LocalTime.of(15, 30),
                arrivalTime = LocalTime.of(17, 0)
            )
        )
    }

    fun refreshRoutes() {
        loadRoutes()
    }
}