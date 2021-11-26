package com.example.partyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_partys_layout.*
import kotlinx.android.synthetic.main.single_party.*

class AllParties : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_partys_layout)

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


