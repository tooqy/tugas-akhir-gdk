package com.tooqy.football_api.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Adapter.MainAdapter
import com.tooqy.football_api.Interface.MainView
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Presenter.MainPresenter
import com.tooqy.football_api.R
import kotlinx.android.synthetic.main.fragment_match_next.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx


class NextMatchFragment : Fragment(), MainView {
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_match_next, container, false)
    }

    companion object {
        fun getInstance(): NextMatchFragment = NextMatchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter(events) {
            ctx.startActivity<DetailMatchActivity>(
                    "idEvent" to it.idEvent,
                    "homeId" to it.homeId,
                    "awayId" to it.awayId
            )
        }

        main_list_view_next.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        main_list_view_next.adapter = adapter

        val gson = Gson()
        val request = ApiRepository()

        presenter = MainPresenter(this, request, gson)
        presenter.getEventList("4328", "next")
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamList(data: List<Event>) {
//        Log.e("DATA_API", "Data : " + data)
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }


}
