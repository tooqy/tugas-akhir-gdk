package com.tooqy.football_api.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tooqy.football_api.Model.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

import com.tooqy.football_api.R.id.team_badge
import com.tooqy.football_api.R.id.team_name

class TeamsAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder2 {
        return TeamViewHolder2(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder2, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }

}

class TeamViewHolder2(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(teams: Team, listener: (Team) -> Unit) {
        Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName
        itemView.onClick { listener(teams) }
    }
}