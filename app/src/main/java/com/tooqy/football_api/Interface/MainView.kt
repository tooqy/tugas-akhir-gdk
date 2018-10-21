package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.Event

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Event>)
}