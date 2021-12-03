package com.example.partyapp.parties

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.LifecycleOwner
import com.example.partyapp.partydetails.PartyInfo
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.R


class PartyAdapter(val Parties :List<Party>, val lf: LifecycleOwner) :RecyclerView.Adapter<PartyAdapter.PartyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.single_party,parent,false)
        return PartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.partyBtn.text = Parties[position].name
        holder.partyLoc.text = Parties[position].location
        holder.partTime.text = Parties[position].time
        val partyId=Parties[position].uid

        holder.partyBtn.setOnClickListener{
            val intent = Intent(it.context, PartyInfo::class.java)
            intent.putExtra("id",partyId)
            it.context.startActivity(intent)
            }

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