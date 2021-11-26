package com.example.partyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ParticipantsAdapter(val Participants :List<Party>) :RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.single_party,parent,false)
        return ParticipantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        //maybe wrong "textname" should be itemview I thinkkk!!
        holder.textName.text = Participants[position].name
        //holder.itemView.text

        //holder.textName.text=Party.title
    }

    override fun getItemCount(): Int {
        return Participants.size
    }

    class ParticipantsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textName= itemView.findViewById<TextView>(R.id.button_with_partyname)
    }
}