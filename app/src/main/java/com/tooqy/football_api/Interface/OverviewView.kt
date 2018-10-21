package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.DetailTeam

interface OverviewView {

    fun showLoading()

    fun hideLoading()

    fun showDetailTeamResponse(data: List<DetailTeam>)

    fun showDetailTeamError()

}