package com.tooqy.football_api.Favorite

class FavouriteTeam(
        val teamId: Int?,
        val teamName: String?,
        val teamBadge: String?) {

    companion object {
        const val TABLE_NAME: String = "TEAM_FAVORITE_X"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }

}