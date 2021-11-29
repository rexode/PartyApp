package com.example.partyapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.FragmentActivity





class PartyAdapter(val Parties :List<Party>) :RecyclerView.Adapter<PartyAdapter.PartyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.single_party,parent,false)
        return PartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        //maybe wrong "textname" should be itemview I thinkkk!!
        holder.partyBtn.text = Parties[position].name
        holder.partyLoc.text = Parties[position].location
        holder.partTime.text = Parties[position].time
        val partyId=Parties[position].uid
        holder.partyBtn.setOnClickListener{
            var overlay=  PartyInfo()
            var args : Bundle = Bundle()
            args.putString("id", partyId)
            overlay.arguments = args
            overlay.show((holder.partyBtn.getContext() as FragmentActivity).supportFragmentManager,
            "partyOverlay")
        }
        //holder.itemView.text

        //holder.textName.text=Party.title
    }

    override fun getItemCount(): Int {
        return Parties.size
    }

    class PartyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val partyBtn= itemView.findViewById<Button>(R.id.button_with_partyname)
        val partyLoc= itemView.findViewById<TextView>(R.id.textedit_party_location)
        val partTime= itemView.findViewById<TextView>(R.id.textview_party_time)
    }
}