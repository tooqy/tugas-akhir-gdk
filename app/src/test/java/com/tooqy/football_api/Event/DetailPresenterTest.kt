package com.tooqy.football_api.Event

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.Interface.DetailView
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Model.EventResponse
import com.tooqy.football_api.Presenter.DetailPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class DetailPresenterTest {

    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailPresenter

    companion object {
        const val LEAGUE: String = "4328"
        const val EVENT_ID: String = "133600"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(LEAGUE, EVENT_ID)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventDetail(LEAGUE, EVENT_ID)

        verify(view).showDetail(events)
    }
}