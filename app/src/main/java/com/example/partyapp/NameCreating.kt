package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NameCreating : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_name_fragment)
        database = FirebaseDatabase.getInstance().getReference("Users")
        val id =intent.getStringExtra("id")
        val email =intent.getStringExtra("email")

        //val textview=findViewById<TextView>(R.id.prueba)
        //textview.setText(email)
        val intent = Intent(this, MainActivity::class.java)
        var editText=findViewById<EditText>(R.id.name_editText)
        var button=findViewById<Button>(R.id.button_done)

        button.setOnClickListener {
            if (id != null) {
                val user = User(email, editText.text.toString())
                database.child(id).setValue(user)
                intent.putExtra("UserId", id)
                startActivity(intent)

            }
        }
    }
}