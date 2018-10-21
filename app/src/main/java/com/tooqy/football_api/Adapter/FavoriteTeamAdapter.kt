package com.tooqy.football_api.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tooqy.football_api.Favorite.FavouriteTeam
import com.tooqy.football_api.R

class FavoriteTeamAdapter(val datas: List<FavouriteTeam>,
                          val listener: (FavouriteTeam) -> Unit): RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTeam = view.findViewById<TextView>(R.id.tv_team)
        val ivTeam = view.findViewById<ImageView>(R.id.iv_team)

        fun bindItems(listener: (FavouriteTeam) -> Unit, data: FavouriteTeam) {
            tvTeam.text = data.teamName

            Glide.with(itemView.context)
                    .load(data.teamBadge)
                    .into(ivTeam)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_team_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: FavoriteTeamAdapter.ViewHolder, position: Int) {
        holder.bindItems(listener, datas.get(position))
    }
}