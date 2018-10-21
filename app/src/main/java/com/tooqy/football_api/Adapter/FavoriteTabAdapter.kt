package com.tooqy.football_api.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.tooqy.football_api.View.FavouriteEventsFragment
import com.tooqy.football_api.View.FavouriteTeamFragment

class FavoriteTabAdapter (fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return FavouriteEventsFragment.getInstance()
        } else {
            return FavouriteTeamFragment.getInstance()
        }

    }

    override fun getCount(): Int {
        return 2
    }
}