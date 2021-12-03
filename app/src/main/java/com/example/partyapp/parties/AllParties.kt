package com.example.partyapp.parties

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp.*
import com.example.partyapp.friendlist.MyProfile
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.partydetails.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.partyapp.login.MainActivity
import com.example.partyapp.partydetails.PartyInfoDialogFragment
import kotlinx.android.synthetic.main.all_partys_layout.*

class AllParties : AppCompatActivity() {


    private lateinit var viewModel: PartyViewModel
    private lateinit var reference: DatabaseReference
    private lateinit var idG: String
    private lateinit var nameG: String
    private var actualUser= User()

    public fun getId() : String{
        return idG
    }

    public fun getName() : String{
        return nameG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_partys_layout)


        val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
        val uidRestored = sp.getString("key", "")


        actionBar?.setTitle("All partys")
        supportActionBar?.setTitle("All Partys")

        reference = FirebaseDatabase.getInstance().getReference("Parties")
        viewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        val id=intent.getStringExtra("id")
        if (id != null) {
            idG = id
        }
        var name: String? =id

        viewModel.getUser(id!!).observe(this,{user->

            name=user.name



            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val edit : SharedPreferences.Editor = sp.edit()
            edit.putString("username", name)
            edit.apply()



            nameG = name.toString()
            actualUser=user
            actualUser.id=id
            name=actualUser.name
            user_name_greeting.setText(name)})
        var liveList:List<Party>
        viewModel.getParties().observe(this,{list-> liveList = list
            val adapter = PartyAdapter(liveList,this)
            recyclerviewAllParties.adapter = adapter
        })

            recyclerviewAllParties.layoutManager = LinearLayoutManager(this)



            button_add_party.setOnClickListener {
                var dialog = PartyInfoDialogFragment()
                var args = Bundle()
                args.putString("userName",actualUser.name)
                args.putString("userEmail",actualUser.email)
                args.putString("userId",actualUser.id)

                dialog.arguments=args
                dialog.show(supportFragmentManager, "customDialog")

            }


        }



    // Menu in tool-bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator:MenuInflater  = menuInflater
        inflator.inflate(R.menu.dot_menu, menu)
        return true
    }


    //LOGOUT AND PROFILE MENU

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_profile -> {

                var intent = Intent(this, MyProfile::class.java)
                startActivity(intent)
            }


            R.id.menu_logout ->
                {
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


