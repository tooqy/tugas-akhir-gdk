package com.tooqy.football_api.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Adapter.MainAdapter
import com.tooqy.football_api.Interface.MainView
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Presenter.MainPresenter
import com.tooqy.football_api.R
import kotlinx.android.synthetic.main.fragment_match_last.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


class LastMatchFragment : Fragment(), MainView {
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_match_last, container, false)
    }

    companion object {
        fun getInstance(): LastMatchFragment = LastMatchFragment()
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

        main_list_view_last.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        main_list_view_last.adapter = adapter

        val gson = Gson()
        val request = ApiRepository()

        presenter = MainPresenter(this, request, gson)
        presenter.getEventList("4328", "last")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search matches"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(qry: String): Boolean {
                toast("Search Submit : " + qry).show()
                return false
            }

            override fun onQueryTextChange(src: String): Boolean {

                toast("Search Change : " + src).show()
//                presenter.searchTeam(newText)
                return false
            }
        })

        searchView?.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                presenter.getTeamData("4328")
                return true
            }
        })
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
