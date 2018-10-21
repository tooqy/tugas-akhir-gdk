package com.tooqy.football_api.View

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.tooqy.football_api.Adapter.FavoriteTabAdapter
import com.tooqy.football_api.Interface.FavoriteView
import com.tooqy.football_api.R

class FavoriteFragment : Fragment(), FavoriteView {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var ivSearch: ImageView
    private lateinit var adapter: FavoriteTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getInstance(): FavoriteFragment = FavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)

        tabLayout.addTab(tabLayout.newTab().setText(activity?.getString(R.string.mi_match)))
        tabLayout.addTab(tabLayout.newTab().setText(activity?.getString(R.string.mi_team)))

        adapter = FavoriteTabAdapter(activity?.supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    viewPager.currentItem = tab.position
                }
            }

        })

        // Inflate the layout for this fragment
        return view
    }
}
