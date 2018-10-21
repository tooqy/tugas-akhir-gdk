package com.tooqy.football_api.Model

import com.google.gson.annotations.SerializedName

data class DetailTeamResponse(
        @SerializedName("teams") val teams: List<DetailTeam>
)