package com.example.mobiles1.storage

import com.example.mobiles1.Movie
import java.util.UUID

object MovieRepository {
    val movies = listOf(
        Movie(
            title = "Diuna: Część druga",
            description = "Książę Paul Atryda przyjmuje przydomek Muad'Dib i rozpoczyna duchowo-fizyczną podróż, by stać się przepowiedzianym w proroctwie wyzwolicielem ludu Diuny."
        ),
        Movie(
            title = "Chłopi",
            description = "Na tle zmieniających się pór roku i sezonowych prac polowych rozgrywają się losy rodziny Borynów i pięknej, tajemniczej Jagny."
        ),
        Movie(
            title = "Zimna wojna",
            description = "Utalentowany kompozytor zakochuje się w młodej członkini zespołu ludowego. Wielkie uczucie utrudniają jednak realia powojennego bloku komunistycznego."
        ),
        Movie(
            title = "La La Land",
            description = "Los Angeles. Pianista jazzowy zakochuje się w początkującej aktorce."
        )
    ) + List(25) {
        Movie(
            title = "The Room",
            description = "Życie poczciwego bankiera zostaje wywrócone do góry nogami w momencie, gdy jego narzeczona zaczyna się spotykać z ich najlepszym przyjacielem."
        )
    }

    fun getMovieById(id: UUID): Movie? {
        return movies.find { it.id == id }
    }
}
