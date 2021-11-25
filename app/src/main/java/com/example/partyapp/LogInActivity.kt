package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.all_partys_layout.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlin.math.log

class LogInActivity : AppCompatActivity() {

/**
    lateinit var SignupButton : Button
    lateinit var LoginButton : Button
    lateinit var edtEmail : EditText
    lateinit var edtPassowrd : EditText
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

/**
        LoginButton= findViewById(R.id.button_login)
        SignupButton= findViewById(R.id.button_createAcc)
        edtEmail=findViewById(R.id.textview_email)
        edtPassowrd=findViewById(R.id.textview_password)

        */

        button_login.setOnClickListener{
            val email=textview_email.text.toString()
            val password= textview_password.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"please enter email and password", Toast.LENGTH_SHORT).show()
            }
            else {
                login(email, password)
            }
        }
        button_createAcc.setOnClickListener{
            var intent= Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

//    just a mockup for testing, will be implemented when database is ready to go.
    private fun login(email:String,password:String){
        if (email=="woto" && password=="da") {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this,"reebs chalichob yleo", Toast.LENGTH_SHORT).show()
        }
    }

}