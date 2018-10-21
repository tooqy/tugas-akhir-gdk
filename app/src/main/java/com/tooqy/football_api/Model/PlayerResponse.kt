package com.tooqy.football_api.Model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
        @SerializedName("player") val player: List<Player>
)