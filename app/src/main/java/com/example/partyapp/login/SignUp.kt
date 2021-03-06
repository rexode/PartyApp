package com.example.partyapp.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.partyapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.signup_layout.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)


        //go back button
        actionBar?.setTitle("")
        supportActionBar?.setTitle("")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        button_createAcc.setOnClickListener {
            when {
                TextUtils.isEmpty(textview_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(textview_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !textview_password.text.toString()
                    .equals(textview_confirm_password.text.toString()) -> {
                    Toast.makeText(
                        this,
                        "Confirm Password in incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = textview_email.text.toString().trim { it <= ' ' }
                    val password = textview_password.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this,
                                    "Sucessfully registered",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val sp: SharedPreferences =
                                    getSharedPreferences("FILE_NAME", MODE_PRIVATE)
                                val edit: SharedPreferences.Editor = sp.edit()
                                edit.putString("key", firebaseUser.uid)
                                edit.apply()

                                val intent = Intent(this, NameCreating::class.java)
                                intent.putExtra("id", firebaseUser.uid)
                                intent.putExtra("email", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }
            }

        }
    }

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}