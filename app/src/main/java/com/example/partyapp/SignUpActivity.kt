package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    lateinit var edtName : EditText
    lateinit var edtPassword: EditText
    lateinit var edtEmail : EditText
    lateinit var SignUpButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)

        edtName=findViewById(R.id.textview_name)
        edtPassword=findViewById(R.id.textview_email)
        edtEmail=findViewById(R.id.textview_password)
        SignUpButton=findViewById(R.id.button_signup)

        SignUpButton.setOnClickListener{
            var intent= Intent(this,MainActivity :: class.java)
            startActivity(intent)
            SignUp(edtName.text.toString(),edtPassword.text.toString(),edtEmail.text.toString())
        }
    }

    private fun SignUp(name:String,email:String,password:String){

    }

}