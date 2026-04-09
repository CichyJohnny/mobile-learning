package com.example.mobiles1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobiles1.storage.RouteRepository
import com.example.mobiles1.ui.theme.Mobiles1Theme
import java.util.UUID

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val routeId = intent.getStringExtra(EXTRA_ROUTE_ID)
        val route = routeId?.let { RouteRepository.getRouteById(UUID.fromString(it)) }

        setContent {
            Mobiles1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    route?.let {
                        RouteDetails(
                            route = route,
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
fun RouteDetails(route: Route, modifier: Modifier = Modifier) {
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
    }
}
