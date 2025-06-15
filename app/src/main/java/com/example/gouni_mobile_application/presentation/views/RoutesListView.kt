package com.example.gouni_mobile_application.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gouni_mobile_application.presentation.viewmodels.RoutesListViewModel
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

@Composable
fun RoutesListView(
    navController: NavController? = null,
    viewModel: RoutesListViewModel = viewModel()
) {
    val routes by viewModel.routes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRoutes()
    }

    val PrimaryColor = Color(0xFF3F51B5)
    val AccentColor = Color(0xFF00BCD4)
    val BackgroundColor = Color(0xFFF5F5F5)
    val CardColor = Color.White
    val TextSecondary = Color(0xFF666666)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mis Rutas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )

            // Botón para crear nueva ruta
            FloatingActionButton(
                onClick = { navController?.navigate("createRoute") },
                containerColor = PrimaryColor,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Crear nueva ruta",
                    tint = Color.White
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
        } else if (routes.isEmpty()) {
            // Estado vacío
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tienes rutas creadas",
                        fontSize = 18.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toca el botón + para crear tu primera ruta",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController?.navigate("createRoute") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor
                        )
                    ) {
                        Text("Crear nueva ruta")
                    }
                }
            }
        } else {
            // Lista de rutas
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(routes) { route ->
                    RouteCard(
                        route = route,
                        onViewRoute = {
                            // Aquí puedes navegar a una vista de detalle de la ruta
                        }
                    )
                }

                // Espaciado final
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun RouteCard(
    route: com.example.gouni_mobile_application.domain.models.Route,
    onViewRoute: () -> Unit
) {
    val PrimaryColor = Color(0xFF3F51B5)
    val AccentColor = Color(0xFF00BCD4)
    val CardColor = Color.White
    val TextSecondary = Color(0xFF666666)
    val TimeBackground = Color(0xFFF0F0F0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardColors(
            containerColor = CardColor,
            contentColor = Color.Black,
            disabledContainerColor = CardColor,
            disabledContentColor = Color.Gray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Encabezado con destinos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Origen
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(AccentColor, RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = route.start,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Destino
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(PrimaryColor, RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = route.end,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }

                // Botón Ver ruta
                Button(
                    onClick = onViewRoute,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF808080)
                    ),
                    modifier = Modifier
                        .height(36.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Ver ruta",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información de horarios
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Días
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatDays(route.days),
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }

                // Horario
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(TimeBackground, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${route.departureTime} - ${route.arrivalTime}",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun formatDays(days: List<DayOfWeek>): String {
    if (days.isEmpty()) return "Sin días"

    return if (days.size == 7) {
        "Todos los días"
    } else if (days.containsAll(listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))) {
        "Lunes a Viernes"
    } else {
        days.joinToString(", ") {
            when(it) {
                DayOfWeek.MONDAY -> "Lu"
                DayOfWeek.TUESDAY -> "Ma"
                DayOfWeek.WEDNESDAY -> "Mi"
                DayOfWeek.THURSDAY -> "Ju"
                DayOfWeek.FRIDAY -> "Vi"
                DayOfWeek.SATURDAY -> "Sa"
                DayOfWeek.SUNDAY -> "Do"
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutesListViewPreview() {
    RoutesListView()
}
