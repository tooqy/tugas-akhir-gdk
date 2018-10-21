package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.DetailTeam

interface DetailTeamView{

    fun showLoading()

    fun hideLoading()

    fun showDetailTeamResponse(data: List<DetailTeam>)

}