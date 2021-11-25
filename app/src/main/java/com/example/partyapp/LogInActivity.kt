package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.math.log

class LogInActivity : AppCompatActivity() {

    lateinit var SignupButton : Button
    lateinit var LoginButton : Button
    lateinit var edtEmail : EditText
    lateinit var edtPassowrd : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        LoginButton= findViewById(R.id.button_login)
        SignupButton= findViewById(R.id.button_signup)
        edtEmail=findViewById(R.id.textview_username_or_email)
        edtPassowrd=findViewById(R.id.textview_password)


        LoginButton.setOnClickListener{
            val email=edtEmail.text.toString()
            val password= edtPassowrd.text.toString()
            if (email==null || email.isEmpty() || password==null || password.isEmpty()){
                Toast.makeText(this,"please enter email and password", Toast.LENGTH_SHORT).show()
            }
            else {
                login(email, password)
            }
        }
        SignupButton.setOnClickListener{
            var intent= Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

//    just a mockup for testing, will be implemented when database is ready to go.
    private fun login(email:String,password:String){
        if (email=="woto" && password=="da") {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this,"reebs chalichob yleo", Toast.LENGTH_SHORT).show()
        }
    }

}