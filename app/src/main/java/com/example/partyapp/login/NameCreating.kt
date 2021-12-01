package com.example.partyapp.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.parties.AllParties
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.R
import com.example.partyapp.partydetails.User
import com.google.firebase.firestore.FirebaseFirestore

class NameCreating : AppCompatActivity() {

    private var db= FirebaseFirestore.getInstance()

    private lateinit var viewModel: PartyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_name_fragment)
        viewModel=ViewModelProvider(this).get(PartyViewModel::class.java)
        val id =intent.getStringExtra("id")
        val email =intent.getStringExtra("email")

       val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
        val edit : SharedPreferences.Editor = sp.edit()
        edit.putString("email", email)
        edit.apply()

        // val textview=findViewById<TextView>(R.id.prueba)
        // textview.setText(email)



        val intent = Intent(this, AllParties::class.java)
        var editText=findViewById<EditText>(R.id.name_editText)
        var button=findViewById<Button>(R.id.button_done)

        button.setOnClickListener {

            if (id != null) {
                val user = User(email,id,editText.text.toString())
                //database.child(id).setValue(user)
                viewModel.getUserLiveData().addName(user,id)
                intent.putExtra("id", id)
                startActivity(intent)

            }
        }
    }
}