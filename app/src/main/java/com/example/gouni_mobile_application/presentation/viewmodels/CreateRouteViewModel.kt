package com.example.gouni_mobile_application.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gouni_mobile_application.domain.models.Route
import com.example.gouni_mobile_application.domain.repository.RouteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

class CreateRouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository
) : ViewModel() {

    var start by mutableStateOf("")
    var end by mutableStateOf("")
    var selectedDays = mutableStateListOf<DayOfWeek>()
    var departureTime by mutableStateOf("")
    var arrivalTime by mutableStateOf("")
    var showConfirmation by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun toggleDaySelection(day: DayOfWeek) {
        if (selectedDays.contains(day)) {
            selectedDays.remove(day)
        } else {
            selectedDays.add(day)
        }
    }

    fun confirmRoute() {
        if (start.isBlank() || end.isBlank() || selectedDays.isEmpty()
            || departureTime.isBlank() || arrivalTime.isBlank()
        ) {
            errorMessage = "Por favor completa todos los campos"
            return
        }

        if (!isValidTimeFormat(departureTime) || !isValidTimeFormat(arrivalTime)) {
            errorMessage = "Por favor ingresa la hora en formato HH:MM"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val route = Route(
                    start = start,
                    end = end,
                    days = selectedDays.toList(),
                    departureTime = LocalTime.parse(departureTime),
                    arrivalTime = LocalTime.parse(arrivalTime)
                )

                routeRepository.saveRoute(route)

                delay(500)
                showConfirmation = true

                // Limpiar formulario después de guardar
                delay(2000)
                clearForm()

            } catch (e: Exception) {
                errorMessage = "Error al guardar la ruta: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun isValidTimeFormat(time: String): Boolean {
        return try {
            LocalTime.parse(time)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun clearForm() {
        start = ""
        end = ""
        selectedDays.clear()
        departureTime = ""
        arrivalTime = ""
        showConfirmation = false
        errorMessage = null
    }

    fun clearError() {
        errorMessage = null
    }
}

// Constructor sin inyección para casos donde no se use Hilt
class CreateRouteViewModelNoHilt : ViewModel() {

    var start by mutableStateOf("")
    var end by mutableStateOf("")
    var selectedDays = mutableStateListOf<DayOfWeek>()
    var departureTime by mutableStateOf("")
    var arrivalTime by mutableStateOf("")
    var showConfirmation by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun toggleDaySelection(day: DayOfWeek) {
        if (selectedDays.contains(day)) {
            selectedDays.remove(day)
        } else {
            selectedDays.add(day)
        }
    }

    fun confirmRoute() {
        if (start.isBlank() || end.isBlank() || selectedDays.isEmpty()
            || departureTime.isBlank() || arrivalTime.isBlank()
        ) {
            errorMessage = "Por favor completa todos los campos"
            return
        }

        if (!isValidTimeFormat(departureTime) || !isValidTimeFormat(arrivalTime)) {
            errorMessage = "Por favor ingresa la hora en formato HH:MM"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                // Simular guardado
                delay(1000)
                showConfirmation = true

                // Limpiar formulario después de guardar
                delay(2000)
                clearForm()

            } catch (e: Exception) {
                errorMessage = "Error al guardar la ruta: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun isValidTimeFormat(time: String): Boolean {
        return try {
            LocalTime.parse(time)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun clearForm() {
        start = ""
        end = ""
        selectedDays.clear()
        departureTime = ""
        arrivalTime = ""
        showConfirmation = false
        errorMessage = null
    }

    fun clearError() {
        errorMessage = null
    }
}