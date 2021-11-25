package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val LogInbutton=findViewById<Button>(R.id.button_login)
        val SignUpButton=findViewById<Button>(R.id.button_createAcc)
        var editTextEmail = findViewById<EditText>(R.id.textview_email)
        var editTextPassword = findViewById<EditText>(R.id.textview_password)
        val intent = Intent(this, SignUp::class.java)



        SignUpButton.setOnClickListener {
            startActivity(intent)
        }
        LogInbutton.setOnClickListener{
            when{
                TextUtils.isEmpty(editTextEmail.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(editTextPassword.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this,
                        "Please enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else->{
                    val email=editTextEmail.text.toString().trim{it<= ' '}
                    val password=editTextPassword.text.toString().trim{it<= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this,
                                    "Logged sucessfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString() ,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }



}