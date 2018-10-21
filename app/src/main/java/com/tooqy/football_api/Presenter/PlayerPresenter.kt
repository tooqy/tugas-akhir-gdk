package com.tooqy.football_api.Presenter

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.CoroutineContextProvider
import com.tooqy.football_api.Interface.PlayerView
import com.tooqy.football_api.Model.PlayerResponse
import com.tooqy.football_api.Model.TeamResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getPlayer(league: String?) {
//        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamPlayer(league)),
                        PlayerResponse::class.java
                )
            }
            view.showPlayerResponse(data.await().player)
        }
//        view.hideLoading()
    }

}