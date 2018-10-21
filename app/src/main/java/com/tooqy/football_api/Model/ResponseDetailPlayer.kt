package com.tooqy.football_api.Model

import com.google.gson.annotations.SerializedName


data class ResponseDetailPlayer(

	@field:SerializedName("players")
	val players: List<PlayersItemDetail?>? = null
)