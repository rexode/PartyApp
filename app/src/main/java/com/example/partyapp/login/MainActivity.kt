package com.example.partyapp.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.partyapp.parties.AllParties
import com.example.partyapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login_layout.*

class MainActivity : AppCompatActivity() {

    var uid: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        actionBar?.hide()
        supportActionBar?.hide()



        var preferences: SharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE)
        var checkboxS = preferences.getString("remember", "")


        if(checkboxS.toString() == "true"){

            // get data from share SharePreference
            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val uidRestored = sp.getString("key", "")

            val intent = Intent(this, AllParties::class.java)
            intent.putExtra("id",uidRestored)


            startActivity(intent)
            finish()


        } else if(checkboxS.equals("false")){
            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show()

        }






        /**
        val LogInbutton=findViewById<Button>(R.id.button_login)
        val SignUpButton=findViewById<Button>(R.id.button_createAcc)
        var editTextEmail = findViewById<EditText>(R.id.textview_email)
        var editTextPassword = findViewById<EditText>(R.id.textview_password)
        val intent = Intent(this, SignUp::class.java)
         */

        button_createNewAcc.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        button_login.setOnClickListener{
            when{
                TextUtils.isEmpty(textview_email.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(textview_password.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this,
                        "Please enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else->{
                    val email=textview_email.text.toString().trim{it<= ' '}
                    val password=textview_password.text.toString().trim{it<= ' '}

                  /*  val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
                    val edit : SharedPreferences.Editor = sp.edit()
                    edit.putString("email", email)
                    edit.apply()

                   */


                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!



                                Toast.makeText(
                                    this,
                                    "Logged sucessfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // save data to share SharePreference
                                val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
                                val edit : SharedPreferences.Editor = sp.edit()
                                edit.putString("key", firebaseUser.uid)
                                edit.apply()


                                val intent = Intent(this, AllParties::class.java)
                                intent.putExtra("id",firebaseUser.uid)
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



        //Checkbox
        checkbox_rememberme.setOnCheckedChangeListener { compoundButton, isChecked ->

            if(isChecked){
                var preferences: SharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                var editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("remember", "true")
                editor.apply()

                Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show()


            } else if(!isChecked) {
                var preferences: SharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                var editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("remember", "false")
                editor.apply()

                Toast.makeText(this, "Unchecked", Toast.LENGTH_SHORT).show()
            }

        }


    }


}