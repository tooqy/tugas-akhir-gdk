package com.tooqy.football_api.Presenter

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.CoroutineContextProvider
import com.tooqy.football_api.Interface.DetailPlayerView
import com.tooqy.football_api.Model.EventResponse
import com.tooqy.football_api.Model.ResponseDetailPlayer
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPlayerPresenter(val view: DetailPlayerView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailPlayer(id: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getDetailPlayer(id)),
                        ResponseDetailPlayer::class.java
                )
            }
            view.showPlayerResponse(data.await().players)
        }

    }

}