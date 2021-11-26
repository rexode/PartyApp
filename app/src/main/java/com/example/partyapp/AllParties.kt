package com.example.partyapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_partys_layout.*
import kotlinx.android.synthetic.main.single_party.*

class AllParties : AppCompatActivity() {
    private lateinit var viewModel: PartyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_partys_layout)
        viewModel= ViewModelProvider(this).get(PartyViewModel::class.java)

        val id=intent.getStringExtra("id")
        var name: String? =id
        
        viewModel.getUser(id!!).observe(this,{user->
            Toast.makeText(this,"hola",Toast.LENGTH_SHORT).show()
            name=user.name})
        user_name_greeting.setText(name)

            var partyList = mutableListOf(
                Party("aId", "aName", "atime", "ahere"),
                Party("bId", "bName", "btime", "bhere"),
                Party("cId", "cName", "ctime", "chere"),
                Party("dId", "dName", "dtime", "dhere"),
                Party("eId", "eName", "etime", "ehere"),
            )

            val adapter = PartyAdapter(partyList)
            recyclerviewAllParties.adapter = adapter
            recyclerviewAllParties.layoutManager = LinearLayoutManager(this)



            button_add_party.setOnClickListener {
                var dialog = PartyInfoDialogFragment()
                dialog.show(supportFragmentManager, "customDialog")
                //partyList.add(Party("new", "new", "new", "new"))
                //adapter.notifyItemInserted(partyList.size - 1)
            }
        }
    }


