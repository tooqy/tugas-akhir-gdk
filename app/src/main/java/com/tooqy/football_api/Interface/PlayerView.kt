package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.Player

interface PlayerView{

    fun showLoading()

    fun hideLoading()

    fun showPlayerResponse(data: List<Player>)

    fun showPlayerError()

}