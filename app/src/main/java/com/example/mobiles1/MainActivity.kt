package com.example.mobiles1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobiles1.DetailsActivity.Companion.EXTRA_ROUTE_ID
import com.example.mobiles1.ui.theme.Mobiles1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobiles1Theme {
                val viewModel: RouteViewModel = viewModel()
                val routes by viewModel.allRoutes.collectAsState(initial = emptyList())

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current
                    val isTablet = configuration.smallestScreenWidthDp >= 600

                    var selectedRouteId by rememberSaveable { mutableStateOf<String?>(null) }

                    val context = LocalContext.current

                    if (isTablet) {
                        Row(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                            RouteList(
                                routes = routes,
                                onRouteClick = { route -> selectedRouteId = route.id.toString() },
                                modifier = Modifier.weight(1f)
                            )
                            val selectedRoute = routes.find { it.id.toString() == selectedRouteId }
                            
                            if (selectedRoute != null) {
                                RouteDetails(
                                    route = selectedRoute,
                                    viewModel = viewModel,
                                    modifier = Modifier.weight(2f)
                                )
                            } else {
                                Text(
                                    text = "Wybierz trasę z listy",
                                    modifier = Modifier.weight(2f).padding(16.dp)
                                )
                            }
                        }
                    } else {
                        RouteList(
                            routes = routes,
                            onRouteClick = { route ->
                                val intent = Intent(context, DetailsActivity::class.java)
                                intent.putExtra(EXTRA_ROUTE_ID, route.id.toString())
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RouteList(routes: List<Route>, onRouteClick: (Route) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(routes, key = { it.id }) { route ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onRouteClick(route) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = route.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
