package com.example.partyapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserLiveData(): LiveData<User>(){
    private var reference: DatabaseReference
    init {
        // Initialize LiveData with empty list
        // Connect to database and create a reference to "notes" node
        val database = Firebase
            .database("https://partyapp-4386a-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)
        reference = FirebaseDatabase.getInstance().getReference("Users")
    }
    fun addName(user:User,id:String,context:Context) {
        // Get a new unique key from database
        if (id != null) {
            // Write new value to database under path /notes/$uid
            reference.child(user.email!!).setValue(user).addOnSuccessListener {

                    Toast.makeText(context,"great",Toast.LENGTH_LONG).show()
            }
            // We don't need to handle data or UI changes here,
            // because once data is changes onDataChange() from getNotes() will be called
        }

    }
    fun updateName(user:User,id:String) {
        if (id != null) {
            // Update note under path /notes/$uid
            reference.child(id).updateChildren(user.toMap())
        }

    }
    fun getUser(id:String?):UserLiveData{
        if(id!=null ){
            reference.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    this.value?.email=it.child("email").value.toString()
                    this.value?.name=it.child("name").value.toString()
                }

            }
        }
        return this
    }
}