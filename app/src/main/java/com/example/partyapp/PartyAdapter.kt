package com.example.partyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PartyAdapter(val Parties :List<Party>) :RecyclerView.Adapter<PartyAdapter.PartyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.single_party,parent,false)
        return PartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        //maybe wrong "textname" should be itemview I thinkkk!!
        holder.textName.text = Parties[position].name
        //holder.itemView.text

        //holder.textName.text=Party.title
    }

    override fun getItemCount(): Int {
        return Parties.size
    }

    class PartyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textName= itemView.findViewById<TextView>(R.id.button_with_partyname)
    }
}