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
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.participant_textview.view.*


class ParticipantAdapter(val Parties :List<User>) :RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.participant_textview,parent,false)
        return ParticipantViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        //holder.itemView.textView_participant1.text = Parties[position].name
        holder.textName.text = Parties[position].name
    }


        //maybe wrong "textname" should be itemview I thinkkk!!
        //var partyModel= PartyViewModel()
        //holder.partyBtn.text = Parties[position].name
        //holder.partyLoc.text = Parties[position].location
        //holder.partTime.text = Parties[position].time
       // val partyId=Parties[position].uid
        //holder.partyBtn.setOnClickListener{
        //    partyModel.findParty(partyId,lf,(holder.partyBtn.getContext() as FragmentActivity).supportFragmentManager)
        //}
        //holder.itemView.text

        //holder.textName.text=Party.title

    override fun getItemCount(): Int {
        return Parties.size
    }

    class ParticipantViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.textView_participant1)
    }

}