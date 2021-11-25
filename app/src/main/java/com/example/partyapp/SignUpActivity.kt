package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.signup_layout_animated.*

class SignUpActivity : AppCompatActivity() {

    /**
    lateinit var edtName :
    lateinit var edtPassword: EditText
    lateinit var edtEmail : EditText
    lateinit var SignUpButton : Button
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout_animated)

        /**
        edtPassword=findViewById(R.id.textview_email)
        edtEmail=findViewById(R.id.textview_password)
        SignUpButton=findViewById(R.id.button_createAcc)
        */

        button_createAcc.setOnClickListener{
            var intent= Intent(this,MainActivity :: class.java)
            startActivity(intent)
            SignUp(textview_password.text.toString(),textview_email.text.toString())
        }
    }

    private fun SignUp(email:String,password:String){

    }

}