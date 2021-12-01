package com.example.partyapp.partydetails

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.partyapp.R
import com.example.partyapp.friendlist.OtherProfile


class FriendsListAdapter(val Users :List<User>) :RecyclerView.Adapter<FriendsListAdapter.FriendListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val view : View=LayoutInflater.from(parent.context).inflate(R.layout.participant_textview,parent,false)
        return FriendListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        //holder.itemView.textView_participant1.text = Parties[position].name
        holder.textName.text = Users[position].name
        //holder.textName.setOnClickListener{
        //    val intent = Intent(it.context, OtherProfile::class.java)
        //    intent.putExtra("id",Users[position].id)
       //     it.context.startActivity(intent)
        //}
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
        return Users.size
    }

    class FriendListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.textView_participant1)
    }

}