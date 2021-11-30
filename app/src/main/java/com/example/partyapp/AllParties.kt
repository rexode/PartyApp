package com.example.partyapp

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*
import kotlinx.android.synthetic.main.single_party.*
import androidx.lifecycle.Observer
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
        setContentView(R.layout.all_partys_layout)


        var token = getSharedPreferences("username", Context.MODE_PRIVATE)


        actionBar?.setTitle("All partys")
        supportActionBar?.setTitle("All Partys")

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



      // **********************************REMOVED THIS BUTTON ******************************************************************************************************

       /*  button_view_friends_list.setOnClickListener{
            var dialog = FriendsListDialogFragment(this)
            dialog.show(supportFragmentManager,"friendsListFragment")
            /* in the end i decided having it as a dialogfragment would be easiest, i did it through several
            different ways like normal fragment and separate layout, but this was the one i thought worked the smoothest */

        }

        */

        }



    // Menu in tool-bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator:MenuInflater  = menuInflater
        inflator.inflate(R.menu.dot_menu, menu)
        return true
    }


    // **********************************LOGOUT AND PROFILE MENU********************************************************************************

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile -> {
                Toast.makeText(this, "Open Profile", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }


            R.id.logout ->
                {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                    var preferences: SharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                    var editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("remember", "false")
                    editor.apply()


                    var intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }

        }
        return super.onOptionsItemSelected(item)
    }

}


