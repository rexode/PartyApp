package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.login_layout.*

class SignUpActivity : AppCompatActivity() {

    /**
    lateinit var edtName :
    lateinit var edtPassword: EditText
    lateinit var edtEmail : EditText
    lateinit var SignUpButton : Button
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)

        /**
        edtPassword=findViewById(R.id.textview_email)
        edtEmail=findViewById(R.id.textview_password)
        SignUpButton=findViewById(R.id.button_createAcc)
        */

        button_createNewAcc.setOnClickListener{
            var intent= Intent(this,MainActivity :: class.java)
            startActivity(intent)
            SignUp(textview_password.text.toString(),textview_email.text.toString())
        }
    }

    private fun SignUp(email:String,password:String){

    }

}