package com.tooqy.football_api.Event

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Api.TheSportDBApi
import com.tooqy.football_api.Interface.MainView
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Model.EventResponse
import com.tooqy.football_api.Presenter.MainPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventsPresenterTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter
    companion object {
        const val LEAGUE: String = "4328"
        const val EVT_TYPE: String = "last"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun testGetTeamList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEvents(LEAGUE, EVT_TYPE)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventList(LEAGUE, EVT_TYPE)

        verify(view).showTeamList(events)
    }
}