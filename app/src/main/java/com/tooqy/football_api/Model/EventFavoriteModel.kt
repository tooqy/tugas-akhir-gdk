package com.tooqy.football_api.Model

import android.content.Context
import com.tooqy.football_api.Favorite.FavoriteEvent
import com.tooqy.football_api.Database.database
import org.jetbrains.anko.db.*


class EventFavoriteModel(val context: Context?) {

    fun add(event: Event) {
        context?.database?.use {
            insert(FavoriteEvent.TABLE,
                    FavoriteEvent.ID to event.idEvent,
                    FavoriteEvent.DATE to event.dateEvent,
                    FavoriteEvent.HOMENAME to event.homeTeam,
                    FavoriteEvent.HOMESCORE to event.homeScore,
                    FavoriteEvent.AWAYNAME to event.awayTeam,
                    FavoriteEvent.AWAYSCORE to event.awayScore
            )
        }
    }

    fun remove(event: Event) {
        context?.database?.use {
            delete(FavoriteEvent.TABLE, "(${FavoriteEvent.ID} = {id})",
                    "id" to event.idEvent)
        }
    }

    fun isExist(event: Event): Boolean {
        var found = false
        context?.database?.use {
            val e = select(FavoriteEvent.TABLE)
                    .whereArgs("(${FavoriteEvent.ID} = {id})", "id" to event.idEvent)
                    .parseOpt(classParser<FavoriteEvent>())

            if (e != null) {
                found = true
            }
        }
        return found
    }

    fun getAll(): List<FavoriteEvent> {
        lateinit var data: List<FavoriteEvent>
        context?.database?.use {
            data = select(FavoriteEvent.TABLE)
                    .orderBy(FavoriteEvent.ID, SqlOrderDirection.DESC)
                    .parseList(classParser<FavoriteEvent>())
        }
        return data
    }
}