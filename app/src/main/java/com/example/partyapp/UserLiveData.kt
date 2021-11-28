package com.example.partyapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*

class UserLiveData(): LiveData<User>(){
    private var reference: DatabaseReference
    init {
        reference = FirebaseDatabase.getInstance().getReference("Users")
    }
    fun addName(user:User,id:String) {
        if (id != null) {
            reference.child(id).setValue(user)
            reference.child(id).child("friendsList")
        }

    }
    fun updateName(user:User,id:String) {
        if (id != null) {
            // Update note under path /notes/$uid
            reference.child(id).updateChildren(user.toMap())
        }

    }
    fun getUser(id:String):UserLiveData{
            /*reference.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    this.value?.email= it.child("email").value as String?
                    this.value?.name= it.child("name").value as String?
            }
        }.addOnFailureListener{
                this.value?.email="ssss"
                this.value?.name="daf"
            }*/
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var newUser:User
                snapshot.children.forEach {
                    if (it != null) {
                        val user = it.getValue<User>()!!
                            .also { note -> if(it.key ==id){
                                newUser=User(note.email,note.name)
                                value=newUser
                            }                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        return this
    }

}