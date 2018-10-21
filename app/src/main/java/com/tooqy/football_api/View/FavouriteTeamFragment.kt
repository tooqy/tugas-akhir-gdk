package com.tooqy.football_api.View

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tooqy.football_api.Adapter.FavoriteTeamAdapter
import com.tooqy.football_api.Database.database
import com.tooqy.football_api.Favorite.FavouriteTeam
import com.tooqy.football_api.R
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class FavouriteTeamFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: FavoriteTeamAdapter

    var favorites: MutableList<FavouriteTeam> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getInstance(): FavouriteTeamFragment = FavouriteTeamFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favourite_team, container, false)

        recyclerView = view.findViewById(R.id.rv_team)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        adapter = FavoriteTeamAdapter(favorites, {
            toast("DETAIL NII").show()
            startActivity<DetailTeamActivity>(
                    "LEAGUE_ID" to it.teamId.toString()
            )
        })
        recyclerView.adapter = adapter

        showFavorite()

        return view
    }

    private fun showFavorite() {
        try {
            context?.database?.use {
                val queryBuilder = select(FavouriteTeam.TABLE_NAME)
                val result = queryBuilder.parseList(classParser<FavouriteTeam>())
                favorites.addAll(result)
                adapter.notifyDataSetChanged()
            }

        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

}
