package com.example.partyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.all_partys_layout.*

class AllParties : AppCompatActivity() {
    private lateinit var viewModel: PartyViewModel
    private lateinit var reference: DatabaseReference
    private lateinit var idG: String
    private lateinit var nameG: String

    public fun getId() : String{
        return idG
    }

    public fun getName() : String{
        return nameG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout .all_partys_layout)
        reference = FirebaseDatabase.getInstance().getReference("Parties")
        viewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        //reference = FirebaseDatabase.getInstance().getReference("Users")
        val id=intent.getStringExtra("id")
        if (id != null) {
            idG = id
        }
        var name: String? =id
        /*reference.child(id!!).get().addOnSuccessListener {
            if(it.exists()){
                name= it.child("name").value as String?
            }}*/
        viewModel.getUser(id!!).observe(this,{user->
            Toast.makeText(this,user.name,Toast.LENGTH_SHORT).show()
            name=user.name
            nameG = name.toString()
            user_name_greeting.setText(name)})
        var liveList:List<Party>
        viewModel.getParties().observe(this,{list-> liveList = list
            val adapter = PartyAdapter(liveList)
            recyclerviewAllParties.adapter = adapter
        })
            var partyList = mutableListOf(
                Party("aId", "aName", "atime", "ahere"),
                Party("bId", "bName", "btime", "bhere"),
                Party("cId", "cName", "ctime", "chere"),
                Party("dId", "dName", "dtime", "dhere"),
                Party("eId", "eName", "etime", "ehere"),
            )

            //recyclerviewAllParties.adapter = adapter
            recyclerviewAllParties.layoutManager = LinearLayoutManager(this)



            button_add_party.setOnClickListener {
                var dialog = PartyInfoDialogFragment()
                dialog.show(supportFragmentManager, "customDialog")
                //partyList.add(Party("new", "new", "new", "new"))
                //adapter.notifyItemInserted(partyList.size - 1)
            }

        button_view_friends_list.setOnClickListener{
            var dialog = FriendsListDialogFragment(this)
            dialog.show(supportFragmentManager,"friendsListFragment")
            /* in the end i decided having it as a dialogfragment would be easiest, i did it through several
            different ways like normal fragment and separate layout, but this was the one i thought worked the smoothest */

        }

        /*button_with_partyname.setOnClickListener{
            Toast.makeText(this,"cheers",Toast.LENGTH_SHORT)
        }*/
        }
    }


