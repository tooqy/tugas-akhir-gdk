package com.tooqy.football_api.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.tooqy.football_api.Adapter.PlayerAdapter
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Interface.PlayerView
import com.tooqy.football_api.Model.Player
import com.tooqy.football_api.Presenter.PlayerPresenter
import com.tooqy.football_api.Presenter.TeamsPresenter
import com.tooqy.football_api.R
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class PlayerFragment : Fragment(), PlayerView {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var presenter: PlayerPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: PlayerAdapter

    var datas: MutableList<Player> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(OverviewFragment.ARG_PARAM1)
            mParam2 = arguments!!.getString(OverviewFragment.ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        recyclerView = view.findViewById(R.id.rv_player)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        adapter = PlayerAdapter(datas) {
            toast("VIEW DETAIL PLAYER : "+it.idPlayer).show()
            startActivity<DetailPlayerActivity>(
                    "LEAGUE_ID" to it.idPlayer
            )
        }
        recyclerView.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)

        presenter.getPlayer(mParam1!!)

        toast(mParam1!!)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.getPlayer(mParam1!!)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showPlayerResponse(data: List<Player>) {
        datas.clear()
        datas.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showPlayerError() {
    }

    companion object {
        val ARG_PARAM1 = "param1"
        val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): PlayerFragment {
            val fragment = PlayerFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}
