package com.tooqy.football_api.View

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.gson.Gson
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Interface.OverviewView
import com.tooqy.football_api.Model.DetailTeam
import com.tooqy.football_api.Model.DetailTeamResponse
import com.tooqy.football_api.Presenter.OverviewPresenter
import com.tooqy.football_api.Presenter.TeamsPresenter
import com.tooqy.football_api.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class OverviewFragment : Fragment(), OverviewView {

    private lateinit var presenter: OverviewPresenter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var tvDesc: TextView

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        tvDesc = view.findViewById(R.id.tv_overview_club)

        val request = ApiRepository()
        val gson = Gson()
        presenter = OverviewPresenter(this, request, gson)

        presenter.getDetailTeam(mParam1!!)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.getDetailTeam(mParam1!!)
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

    override fun showDetailTeamResponse(data: List<DetailTeam>) {
        val model = data.get(0)

        tvDesc.text = model.strStadiumDescription
    }

    override fun showDetailTeamError() {
        toast("Team Error")
    }

    companion object {

//        fun getInstance(): OverviewFragment = OverviewFragment()

        val ARG_PARAM1 = "param1"
        val ARG_PARAM2 = "param2"
        fun newInstance(param1: String, param2: String): OverviewFragment {
            val fragment = OverviewFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}
