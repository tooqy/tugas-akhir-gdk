package com.tooqy.football_api.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooqy.football_api.Favorite.FavoriteEvent
import com.tooqy.football_api.R
import kotlinx.android.synthetic.main.item_match.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

class FavoriteEventAdapter(private val favorite: List<FavoriteEvent>, private val listener: (FavoriteEvent) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(favorite: FavoriteEvent, listener: (FavoriteEvent) -> Unit) {
        var score1: String = "0"
        if (favorite.homeScore != null)
            score1 = favorite.homeScore.toString()


        var score2: String = "0"
        if (favorite.awayScore != null)
            score2 = favorite.awayScore.toString()

        itemView.nameTeam1.text = favorite.homeName
        itemView.scoreTeam1.text = score1

        itemView.nameTeam2.text = favorite.awayName
        itemView.scoreTeam2.text = score2

        try {
            val dateformat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateformat.parse(favorite.date)
            val format = SimpleDateFormat("EEEE, dd MMM yyyy")
            val fromDate = format.format(date)
            itemView.textDate.text = fromDate
        } catch (e: ParseException) {
            e.printStackTrace()
            itemView.textDate.text = favorite.date
        }

        itemView.setOnClickListener { listener(favorite) }
    }
}