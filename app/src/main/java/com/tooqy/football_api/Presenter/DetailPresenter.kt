package com.tooqy.football_api.Presenter

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.CoroutineContextProvider
import com.tooqy.football_api.Interface.DetailView
import com.tooqy.football_api.Model.EventResponse
import com.tooqy.football_api.Model.TeamResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventDetail(league: String?, eventID: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEventDetail(league, eventID)),
                        EventResponse::class.java
                )
            }
            view.showDetail(data.await().events)
        }
    }

    fun getLogoTeam(teamID: String, type: Int) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getLogo(teamID)),
                    TeamResponse::class.java
            )

            uiThread {
                view.showLogo(data.teams, type)
            }
        }
    }
}
