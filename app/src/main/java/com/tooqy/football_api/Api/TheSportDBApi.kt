package com.tooqy.football_api.Api

import android.net.Uri
import android.util.Log
import com.tooqy.football_api.BuildConfig

object TheSportDBApi {

    fun getEvents(league: String?, type: String?): String {

        val page: String
        if (type.equals("last")) {
            page = "eventspastleague.php"
        } else {
            page = "eventsnextleague.php"
        }
        return parseUrlToGetAPI(page, league)
    }

    fun getEventDetail(league: String?, eventID: String?): String {
        val page: String = "lookupevent.php"
        return parseUrlToGetAPI(page, eventID)
    }

    fun getLogo(teamID: String): String {
        val page: String = "lookupteam.php"
        return parseUrlToGetAPI(page, teamID)
    }


    fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    fun getTeamDetail(teamId: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }

    fun getTeamPlayer(teamID: String?): String{
        Log.e("PLAYER : ",BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + teamID)
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + teamID
    }

    fun getDetailPlayer(playerID: String?): String{
        Log.e("PLAYER DTL : ",BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + playerID)
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + playerID
    }

    fun parseUrlToGetAPI(page: String?, id: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/"+page+"?id=" + id
    }

}