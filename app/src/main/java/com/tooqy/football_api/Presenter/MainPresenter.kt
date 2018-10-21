package com.tooqy.football_api.Presenter

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.CoroutineContextProvider
import com.tooqy.football_api.Interface.MainView
import com.tooqy.football_api.Model.EventResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import java.util.*


class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventList(league: String?, type: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEvents(league, type)),
                        EventResponse::class.java
                )
            }
            view.showTeamList(data.await().events)
//            view.hideLoading()
        }
    }

    fun searchTeam(teamName: String) {
//        mView.showLoading()
    }

    fun getTeamData(teamName: String) {
//        mView.showLoading()
    }
}