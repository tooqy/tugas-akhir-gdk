package com.tooqy.football_api.Model

import com.google.gson.annotations.SerializedName

data class SearchEventResponse(
        @SerializedName("event") val event: List<SearchEvent>
)