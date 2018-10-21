package com.tooqy.football_api.Model

import com.google.gson.annotations.SerializedName


data class Event(

        @SerializedName("idEvent") val idEvent: String,
        @SerializedName("idSoccerXML") val idScore: String,

        @SerializedName("strEvent") val name: String,
        @SerializedName("strFilename") val fileName: String,


        @SerializedName("idHomeTeam") val homeId: String,
        @SerializedName("strHomeTeam") val homeTeam: String,

        @SerializedName("idAwayTeam") val awayId: String,
        @SerializedName("strAwayTeam") val awayTeam: String,

        @SerializedName("intHomeScore") val homeScore: Int?,
        @SerializedName("intAwayScore") val awayScore: Int?,
        @SerializedName("dateEvent") val dateEvent: String,


        @SerializedName("strHomeGoalDetails") val homeGoals: String?,
        @SerializedName("strHomeRedCards") val homeRed: String?,
        @SerializedName("strHomeYellowCards") val homeYellow: String?,
        @SerializedName("strHomeLineupGoalkeeper") val homeGK: String?,
        @SerializedName("strHomeLineupDefense") val homeDF: String?,
        @SerializedName("strHomeLineupMidfield") val homeMF: String?,
        @SerializedName("strHomeLineupForward") val homeFW: String?,
        @SerializedName("strHomeLineupSubstitutes") val homeSubs: String?,
        @SerializedName("strHomeFormation") val homeFormation: String?,
        @SerializedName("strAwayRedCards") val awayRed: String?,
        @SerializedName("strAwayYellowCards") val awayYellow: String?,
        @SerializedName("strAwayGoalDetails") val awayGoals: String?,
        @SerializedName("strAwayLineupGoalkeeper") val awayGK: String?,
        @SerializedName("strAwayLineupDefense") val awayDF: String?,
        @SerializedName("strAwayLineupMidfield") val awayMF: String?,
        @SerializedName("strAwayLineupForward") val awayFW: String?,
        @SerializedName("strAwayLineupSubstitutes") val awaySubs: String?,
        @SerializedName("strAwayFormation") val awayFormation: String?,

        @SerializedName("intHomeShots") val homeShots: String?,
        @SerializedName("intAwayShots") val awayShots: String?

)