package com.example.partyapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.all_partys_layout.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*
import kotlinx.android.synthetic.main.single_party.*

class AllParties : AppCompatActivity() {
    private lateinit var viewModel: PartyViewModel
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_partys_layout)
        reference = FirebaseDatabase.getInstance().getReference("Parties")
        viewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        //reference = FirebaseDatabase.getInstance().getReference("Users")
        val id=intent.getStringExtra("id")
        var name: String? =id
        /*reference.child(id!!).get().addOnSuccessListener {
            if(it.exists()){
                name= it.child("name").value as String?
            }}*/
        viewModel.getUser(id!!).observe(this,{user->
            Toast.makeText(this,user.name,Toast.LENGTH_SHORT).show()
            name=user.name
            user_name_greeting.setText(name)})
        var liveList:List<Party>
        viewModel.getParties().observe(this,{list-> liveList = list
            val adapter = PartyAdapter(liveList,this)
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
        /*button_with_partyname.setOnClickListener{
            Toast.makeText(this,"cheers",Toast.LENGTH_SHORT)
        }*/
        }
    }


