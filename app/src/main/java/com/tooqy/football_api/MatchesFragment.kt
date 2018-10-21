package com.tooqy.football_api


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import android.support.v7.widget.SearchView

import com.tooqy.football_api.Adapter.ViewPagerAdapter
import com.tooqy.football_api.View.LastMatchFragment
import com.tooqy.football_api.View.NextMatchFragment

class MatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    companion object {
        fun getInstance(): MatchesFragment = MatchesFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vPager = view.findViewById<ViewPager>(R.id.viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.populateFragment(NextMatchFragment(), "Next")
        adapter.populateFragment(LastMatchFragment(), "Past")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?

        searchView?.queryHint = "Search matches"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                context?.startActivity<SearchMatchActivity>("query" to query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })


    }

}
