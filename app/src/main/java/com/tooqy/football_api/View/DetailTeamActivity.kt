package com.tooqy.football_api.View

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tooqy.football_api.Adapter.DetailTeamTabAdapter
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Database.database
import com.tooqy.football_api.Favorite.FavoriteEvent
import com.tooqy.football_api.Favorite.FavouriteTeam
import com.tooqy.football_api.Interface.DetailTeamView
import com.tooqy.football_api.Model.DetailTeam
import com.tooqy.football_api.Model.DetailTeamResponse
import com.tooqy.football_api.Presenter.DetailTeamPresenter
import com.tooqy.football_api.R
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    private lateinit var adapter: DetailTeamTabAdapter
    private lateinit var presenter: DetailTeamPresenter

    var menuItem: Menu? = null
    var idTeam: String = ""
    var teamBadge: String = ""
    var teamName: String = ""

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null

        idTeam = intent.getStringExtra("LEAGUE_ID")

        tabs.addTab(tabs.newTab().setText(getString(R.string.overview)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.player)))

        checkFavourite()

        val gson = Gson()
        val request = ApiRepository()
        presenter = DetailTeamPresenter(this, request, gson)

        adapter = DetailTeamTabAdapter(supportFragmentManager, idTeam)
        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewpager.currentItem = tab.position
                }
            }

        })

        presenter.getDetailTeam(idTeam)


        swipe_refresh_layout.setOnClickListener {
            swipe_refresh_layout.isRefreshing = false
            presenter.getDetailTeam(idTeam)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        setFavourite()

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.home -> {
                finish()
                return true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                    isFavorite = false
                } else {
                    addToFavorite()
                    isFavorite = true
                }

                //set favorite
                setFavourite()

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }


    override fun showLoading() {
        swipe_refresh_layout.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh_layout.isRefreshing = false
    }

    override fun showDetailTeamResponse(data: List<DetailTeam>) {

        val model = data.get(0)

        teamName = model.strTeam
        teamBadge = model.strTeamBadge

        tv_club_name.text = model.strTeam
        tv_club_stadium.text = model.strStadium
        tv_club_year.text = model.intFormedYear

        Glide.with(this)
                .load(model.strTeamBadge)
                .into(iv_club)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun setFavourite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun checkFavourite() {
        try {
            database.use {
                val queryBuilder = select(FavouriteTeam.TABLE_NAME)
                        .whereArgs("(${FavouriteTeam.TEAM_ID} = {id})",
                                "id" to idTeam)
                val result = queryBuilder.parseList(classParser<FavouriteTeam>())

                isFavorite = !result.isEmpty()
//                setFavourite()
            }
        } catch (e: SQLiteConstraintException) {
            toast(e.message!!)
        }
    }

    fun addToFavorite() {
        try {
            database.use {
                insert(FavouriteTeam.TABLE_NAME,
                        FavouriteTeam.TEAM_ID to idTeam,
                        FavouriteTeam.TEAM_NAME to teamName,
                        FavouriteTeam.TEAM_BADGE to teamBadge)
            }
            toast("Success Add Team to Favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast("Failed Add Team to Favorite").show()
        }
    }

    fun removeFromFavorite() {
        try {
            database.use {
                delete(FavouriteTeam.TABLE_NAME,
                        "( ${FavouriteTeam.TEAM_ID} = {team_id})",
                        "team_id" to idTeam)
            }
            toast("Success Remove Team from Favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast("Failed Remove Team from Favorite").show()
        }
    }
}
