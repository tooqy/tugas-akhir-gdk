package com.tooqy.football_api.Presenter

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.CoroutineContextProvider
import com.tooqy.football_api.Interface.OverviewView
import com.tooqy.football_api.Model.DetailTeamResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class OverviewPresenter(val view: OverviewView,
                        private val apiRepository: ApiRepository,
                        private val gson: Gson,
                        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailTeam(id: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(id)),
                        DetailTeamResponse::class.java
                )
            }
            view.showDetailTeamResponse(data.await().teams)
        }
    }

}