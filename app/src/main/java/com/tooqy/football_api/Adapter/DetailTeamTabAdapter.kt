package com.tooqy.football_api.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.tooqy.football_api.View.LastMatchFragment
import com.tooqy.football_api.View.NextMatchFragment
import com.tooqy.football_api.View.OverviewFragment
import com.tooqy.football_api.View.PlayerFragment

class DetailTeamTabAdapter (fm: FragmentManager?, val id: String?) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return OverviewFragment. newInstance(id!!, "")

//          return OverviewFragment.newInstance(id!!, "")
        } else {
            return PlayerFragment.newInstance(id!!, "")
//            return LastMatchFragment.getInstance()

        }

    }

    override fun getCount(): Int {
        return 2
    }
}