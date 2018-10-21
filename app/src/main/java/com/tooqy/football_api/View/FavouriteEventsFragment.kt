package com.tooqy.football_api.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.tooqy.football_api.Favorite.FavoriteEvent
import com.tooqy.football_api.Adapter.FavoriteEventAdapter
import com.tooqy.football_api.Presenter.MainPresenter
import com.tooqy.football_api.R
import com.tooqy.football_api.Database.database
import kotlinx.android.synthetic.main.fragment_favorite_events.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx


class FavouriteEventsFragment : Fragment() {

    private var favorites: MutableList<FavoriteEvent> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: FavoriteEventAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_favorite_events, container, false)
    }

    companion object {
        fun getInstance(): FavouriteEventsFragment = FavouriteEventsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteEventAdapter(favorites) {
            ctx.startActivity<DetailMatchActivity>(
                    "idEvent" to it.id.toString(),
                    "homeId" to it.homeID,
                    "awayId" to it.awayID
            )
        }

        main_list_view_fav.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        main_list_view_fav.adapter = adapter

        showFavorite()

    }

    private fun showFavorite() {
        context?.database?.use {
            val result = select(FavoriteEvent.TABLE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
