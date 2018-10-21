package com.tooqy.football_api

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.tooqy.football_api.Interface.MainView
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.Presenter.MainPresenter
import com.tooqy.football_api.View.*
import kotlinx.android.synthetic.main.activity_main_tab.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var leagueName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab)

        var menu: Menu = navigation.menu
        selectMainMenu(menu.getItem(0))
        navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            selectMainMenu(item)
            false
        }
    }

    private fun selectMainMenu(item: MenuItem) {
        item.isChecked = true
        when (item.itemId) {
            R.id.nav_match -> selectedFragment(MatchesFragment.getInstance())
            R.id.nav_team -> selectedFragment(TeamsFragment.getInstance())
            R.id.nav_favor -> selectedFragment(FavoriteFragment.getInstance())
        }
    }

    fun selectedFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamList(data: List<Event>) {
    }

    override fun onBackPressed() {
        finish()
    }


}
