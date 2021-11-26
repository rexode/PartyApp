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
        reference = FirebaseDatabase.getInstance().getReference("Users")
    }
    fun addName(user:User,id:String) {
        if (id != null) {
            reference.child(id!!).setValue(user)
        }

    }
    fun updateName(user:User,id:String) {
        if (id != null) {
            // Update note under path /notes/$uid
            reference.child(id).updateChildren(user.toMap())
        }

    }
    fun getUser(id:String):UserLiveData{
            reference.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    this.value?.email= it.child("email").value as String?
                    this.value?.name= it.child("name").value as String?
            }
        }.addOnFailureListener{
                this.value?.email="ssss"
                this.value?.name="daf"
            }
        return this
    }

}