package com.tooqy.football_api.Presenter

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.CoroutineContextProvider
import com.tooqy.football_api.Interface.TeamsView
import com.tooqy.football_api.Model.TeamResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
//        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
        }
//        view.hideLoading()
    }
}