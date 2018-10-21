package com.tooqy.football_api.Favorite

data class FavoriteEvent(
        val id : Int?,
        val date : String?,
        val homeID : String?,
        val homeName : String?,
        val homeScore : String?,
        val awayID : String?,
        val awayName : String?,
        val awayScore : String?
){
    companion object {
        const val TABLE: String = "EVENT_FAVORITE"
        const val ID: String = "ID"
        const val DATE : String = "DATE"
        const val HOMEID: String = "HOMEID"
        const val HOMENAME : String = "HOMENAME"
        const val HOMESCORE : String = "HOMESCORE"
        const val AWAYID : String = "AWAYID"
        const val AWAYNAME : String = "AWAYNAME"
        const val AWAYSCORE : String = "AWAYSCORE"
    }
}