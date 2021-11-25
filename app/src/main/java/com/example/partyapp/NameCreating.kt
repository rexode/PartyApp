package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NameCreating : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_name_fragment)
        val id =intent.getStringExtra("userId")
        val textview=findViewById<TextView>(R.id.prueba)
        textview.setText(id)
        val intent = Intent(this, Partieslist::class.java)
        var editText=findViewById<EditText>(R.id.name_editText)
        var button=findViewById<Button>(R.id.button_done)
        button.setOnClickListener{
            database.child("Users").child(id!!).setValue(editText.text.toString())
            intent.putExtra("UserId",id)
            startActivity(intent)

        }
    }
}