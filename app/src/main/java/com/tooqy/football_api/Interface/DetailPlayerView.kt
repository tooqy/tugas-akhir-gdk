package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.PlayersItemDetail

interface DetailPlayerView {

    fun showLoading()

    fun hideLoading()

    fun showPlayerResponse(data: List<PlayersItemDetail?>?)

    fun showPlayerError()

}