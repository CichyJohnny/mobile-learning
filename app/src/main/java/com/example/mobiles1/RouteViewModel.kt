package com.example.mobiles1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiles1.storage.AppDatabase
import com.example.mobiles1.storage.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.util.UUID

class RouteViewModel(application: Application) : AndroidViewModel(application) {
    private val routeDao = AppDatabase.getInstance(application).routeDao()

    val allRoutes: Flow<List<Route>> = routeDao.getAllRoutes()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentRoutes = routeDao.getAllRoutes().first()
            if (currentRoutes.isEmpty()) {
                routeDao.insertAll(RouteRepository.routes)
            }
        }
    }

    fun getRouteById(id: UUID): Flow<Route?> = routeDao.getRouteById(id)

    fun startStopwatch(route: Route) {
        if (!route.isRunning) {
            val updatedRoute = route.copy(
                isRunning = true,
                lastStartTime = System.currentTimeMillis()
            )
            viewModelScope.launch {
                routeDao.update(updatedRoute)
            }
        }
    }

    fun stopStopwatch(route: Route) {
        if (route.isRunning) {
            val now = System.currentTimeMillis()
            val passed = now - route.lastStartTime
            val newElapsed = route.elapsedTime + passed
            val updatedRoute = route.copy(
                isRunning = false,
                elapsedTime = newElapsed
            )
            viewModelScope.launch {
                routeDao.update(updatedRoute)
            }
        }
    }

    fun resetStopwatch(route: Route) {
        val updatedRoute = route.copy(
            isRunning = false,
            elapsedTime = 0L,
            lastStartTime = 0L
        )
        viewModelScope.launch {
            routeDao.update(updatedRoute)
        }
    }
}