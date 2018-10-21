package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}