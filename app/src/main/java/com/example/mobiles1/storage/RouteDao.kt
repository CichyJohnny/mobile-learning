package com.example.mobiles1.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mobiles1.Route
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes")
    fun getAllRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM routes WHERE id = :id")
    fun getRouteById(id: UUID): Flow<Route?>

    @Insert
    suspend fun insertAll(routes: List<Route>)

    @Update
    suspend fun update(route: Route)

    @Delete
    suspend fun delete(route: Route)
}