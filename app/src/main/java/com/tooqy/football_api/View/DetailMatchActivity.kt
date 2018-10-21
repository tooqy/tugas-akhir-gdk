package com.tooqy.football_api.View

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.tooqy.football_api.Api.ApiRepository
import com.tooqy.football_api.Interface.DetailView
import com.tooqy.football_api.Favorite.FavoriteEvent
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Model.Team
import com.tooqy.football_api.Presenter.DetailPresenter
import com.tooqy.football_api.R
import com.tooqy.football_api.R.drawable.ic_add_to_favorites
import com.tooqy.football_api.R.drawable.ic_added_to_favorites
import com.tooqy.football_api.R.id.add_to_favorite
import com.tooqy.football_api.R.menu.detail_menu
import com.tooqy.football_api.Database.database
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.text.ParseException
import java.text.SimpleDateFormat

class DetailMatchActivity : AppCompatActivity(), DetailView {
    private lateinit var presenter: DetailPresenter
    private lateinit var event: Event

    private var _eventId: String = ""
    private var _homeId: String = ""
    private var _awayId: String = ""

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false


    private var EVENT_DATE: String = ""
    private var HOME_ID: String = ""
    private var HOME_NAME: String = ""
    private var HOME_SCORE: String = ""
    private var AWAY_ID: String = ""
    private var AWAY_NAME: String = ""
    private var AWAY_SCORE: String = ""

    companion object {
        val EVENTID = "idEvent"
        val HOMEID = "homeId"
        val AWAYID = "awayId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        val varIntent = intent
        _eventId = varIntent.getStringExtra(EVENTID)
        _homeId = varIntent.getStringExtra(HOMEID)
        _awayId = varIntent.getStringExtra(AWAYID)

        favoriteState(_eventId)
        val gson = Gson()
        val request = ApiRepository()

        presenter = DetailPresenter(this, request, gson)
        presenter.getEventDetail("4328", _eventId)
        presenter.getLogoTeam(_homeId, 0)
        presenter.getLogoTeam(_awayId, 1)
    }

    @SuppressLint("SimpleDateFormat")
    override fun showDetail(data: List<Event>) {
        for (i: Int in data.indices) {
            val dt: Event = data.get(i)

            try {
                val dateformat = SimpleDateFormat("yyyy-MM-dd")
                val date = dateformat.parse(dt.dateEvent)
                val format = SimpleDateFormat("EEEE, dd MMM yyyy")
                val fromDate = format.format(date)

                textDate.text = fromDate
                EVENT_DATE = dt.dateEvent

                var score1: String = ""
                if (dt.homeScore != null)
                    score1 = dt.homeScore.toString()

                nameTeam1.text = dt.homeTeam
                HOME_ID = dt.homeId
                HOME_NAME = dt.homeTeam

                scoreTeam1.text = score1
                HOME_SCORE = if (dt.homeScore != null) dt.homeScore.toString() else "0"

                positionTeam1.text = dt.homeFormation
                goalTeam1.text = dt.homeGoals
                shot_team1.text = dt.homeShots
                lineupGK_team1.text = dt.homeGK
                lineupDef_team1.text = dt.homeDF
                lineupMid_team1.text = dt.homeMF
                lineupFW_team1.text = dt.homeFW
                lineupSub_team1.text = dt.homeSubs

                var score2: String = ""
                if (dt.awayScore != null)
                    score2 = dt.awayScore.toString()

                nameTeam2.text = dt.awayTeam
                AWAY_ID = dt.awayId
                AWAY_NAME = dt.awayTeam

                scoreTeam2.text = score2
                AWAY_SCORE = if (dt.awayScore != null) dt.awayScore.toString() else "0"

                positionTeam2.text = dt.awayFormation
                goalTeam2.text = dt.awayGoals
                shot_team2.text = dt.awayShots
                lineupGK_team2.text = dt.awayGK
                lineupDef_team2.text = dt.awayDF
                lineupMid_team2.text = dt.awayMF
                lineupFW_team2.text = dt.awayFW
                lineupSub_team2.text = dt.awaySubs

            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    override fun showLogo(data: List<Team>, type: Int) {
        Log.e("IMAGES", "IMAGES " + data)
        for (i in data.indices) {
            val team = data.get(i)

            when (type) {
                0 -> {
                    Picasso.get().load(team.teamBadge).into(imgTeam1)
                }
                else -> {
                    Picasso.get().load(team.teamBadge).into(imgTeam2)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite(_eventId) else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteEvent.TABLE,
                        FavoriteEvent.ID to _eventId,
                        FavoriteEvent.DATE to EVENT_DATE,

                        FavoriteEvent.HOMEID to HOME_ID,
                        FavoriteEvent.HOMENAME to HOME_NAME,
                        FavoriteEvent.HOMESCORE to HOME_SCORE,

                        FavoriteEvent.AWAYID to AWAY_ID,
                        FavoriteEvent.AWAYNAME to AWAY_NAME,
                        FavoriteEvent.AWAYSCORE to AWAY_SCORE
                )
            }

            toast("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast("Failed to favorite").show()
        }
    }

    private fun removeFromFavorite(idEvent: String) {
        try {
//            var idEvent: String = "576529"

            database.use {
                delete(FavoriteEvent.TABLE, "(ID = {id})",
                        "id" to idEvent)
            }

            toast("Success to delete favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast("Failed to delete favorite").show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(eventID: String) {
        database.use {
            val result = select(FavoriteEvent.TABLE)
                    .whereArgs("(ID = {id})",
                            "id" to eventID)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
