package com.example.mobiles1.storage

import com.example.mobiles1.Route
import java.util.UUID

object RouteRepository {
    val routes = listOf(
        Route(
            title = "Szlak Orlich Gniazd",
            description = "Jeden z najpiękniejszych szlaków turystycznych w Polsce, przebiegający przez Wyżynę Krakowsko-Częstochowską."
        ),
        Route(
            title = "Dolina Pięciu Stawów",
            description = "Wymagająca trasa w Tatrach Wysokich, prowadząca przez malownicze jeziora polodowcowe."
        ),
        Route(
            title = "Bieszczadzkie Połoniny",
            description = "Klasyczna trasa przez Połoninę Wetlińską i Caryńską, oferująca niezapomniane widoki."
        ),
        Route(
            title = "Szlak na Śnieżkę",
            description = "Popularna trasa w Karkonoszach prowadząca na najwyższy szczyt tego pasma górskiego."
        )
    ) + List(25) {
        Route(
            title = "Lokalna trasa nr ${it + 1}",
            description = "Spokojna trasa spacerowa, idealna na weekendowy odpoczynek z rodziną."
        )
    }

    fun getRouteById(id: UUID): Route? {
        return routes.find { it.id == id }
    }
}
