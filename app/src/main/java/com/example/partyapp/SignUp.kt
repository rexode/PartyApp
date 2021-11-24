package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.signup_layout.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)
        val SignUpButton=findViewById<Button>(R.id.button_signup)
        var editTextName = findViewById<EditText>(R.id.textview_name)
        var editTextPassword = findViewById<EditText>(R.id.textview_password)
        var editTextEmail= findViewById<EditText>(R.id.textview_email)

        SignUpButton.setOnClickListener {
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

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this,
                                    "Sucessfully registered",
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