package com.tooqy.football_api.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooqy.football_api.Model.Event
import com.tooqy.football_api.R
import kotlinx.android.synthetic.main.item_match.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

class MainAdapter(private val event: List<Event>, private val listener: (Event) -> Unit) : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int = event.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(event[position], listener)
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(events: Event, listener: (Event) -> Unit) {

        var score1: String = "0"
        if (events.homeScore != null)
            score1 = events.homeScore.toString()


        var score2: String = "0"
        if (events.awayScore != null)
            score2 = events.awayScore.toString()

        itemView.nameTeam1.text = events.homeTeam
        itemView.scoreTeam1.text = score1

        itemView.nameTeam2.text = events.awayTeam
        itemView.scoreTeam2.text = score2

        try {
            val dateformat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateformat.parse(events.dateEvent)
            val format = SimpleDateFormat("EEEE, dd MMM yyyy")
            val fromDate = format.format(date)
            itemView.textDate.text = fromDate
        } catch (e: ParseException) {
            e.printStackTrace()
            itemView.textDate.text = events.dateEvent
        }

//        itemView.textDate.text = this.setDateEvents(events.dateEvent)

        itemView.setOnClickListener { listener(events) }
    }
}


