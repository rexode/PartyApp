package com.example.partyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendsListAdapter(private val fList: MutableList<User>?) : RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_friend, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.friendName.text = fList?.get(position)!!.name
        holder.friendMail.text = fList?.get(position)!!.email


    }


    override fun getItemCount(): Int {
        return fList!!.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var friendName = itemView.findViewById<TextView>(R.id.friend_name)
        var friendMail = itemView.findViewById<TextView>(R.id.friend_mail)
    }
}