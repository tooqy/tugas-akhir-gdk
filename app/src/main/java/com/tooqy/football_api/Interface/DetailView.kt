package com.tooqy.football_api.Interface

import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Model.Team

interface DetailView {
    fun showDetail(data: List<Event>)
    fun showLogo(data:List<Team>, type: Int)
}