package com.example.mobiles1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobiles1.ui.theme.Mobiles1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val routeId = intent.getStringExtra(EXTRA_ROUTE_ID)

        setContent {
            Mobiles1Theme {
                val viewModel: RouteViewModel = viewModel()
                val routeFlow = routeId?.let { viewModel.getRouteById(UUID.fromString(it)) } ?: flowOf(null)
                val route by routeFlow.collectAsState(initial = null)


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    route?.let {
                        RouteDetails(
                            route = it,
                            viewModel = viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_ROUTE_ID = "ROUTE_ID"
    }
}

@Composable
fun RouteDetails(route: Route, viewModel: RouteViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = route.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = route.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Stopwatch(route = route, viewModel = viewModel)
    }
}

@Composable
fun Stopwatch(route: Route, viewModel: RouteViewModel, modifier: Modifier = Modifier) {
    var displayTime by remember(route.elapsedTime, route.isRunning, route.lastStartTime) {
        val initialPassed = if (route.isRunning) System.currentTimeMillis() - route.lastStartTime else 0L
        mutableLongStateOf(route.elapsedTime + initialPassed)
    }

    LaunchedEffect(route.isRunning) {
        while (route.isRunning) {
            delay(100)
            val passed = System.currentTimeMillis() - route.lastStartTime
            displayTime = route.elapsedTime + passed
        }
    }

    val totalSeconds = displayTime / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val timeString = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeString,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { viewModel.startStopwatch(route) }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start")
            }
            IconButton(onClick = { viewModel.stopStopwatch(route) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Stop")
            }
            IconButton(onClick = { viewModel.resetStopwatch(route) }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Przerwij")
            }
        }
    }
}
