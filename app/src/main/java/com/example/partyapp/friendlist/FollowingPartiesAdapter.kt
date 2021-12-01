package com.example.partyapp.partydetails

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.partyapp.R
import com.example.partyapp.friendlist.OtherProfile
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.parties.Party


class FollowingPartiesAdapter(val Parties :List<Party>) :RecyclerView.Adapter<FollowingPartiesAdapter.FollowingPartiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingPartiesViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.single_party,parent,false)
        return FollowingPartiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingPartiesViewHolder, position: Int) {
        //holder.itemView.textView_participant1.text = Parties[position].name
        var partyModel= PartyViewModel()
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

    class FollowingPartiesViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val partyBtn= itemView.findViewById<Button>(R.id.button_with_partyname)
        val partyLoc= itemView.findViewById<TextView>(R.id.textedit_party_location)
        val partTime= itemView.findViewById<TextView>(R.id.textview_party_time)
    }

}