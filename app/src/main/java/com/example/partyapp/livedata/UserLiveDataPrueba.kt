package com.example.partyapp.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore

class UserLiveDataPrueba: LiveData<User>() {
    private var db= FirebaseFirestore.getInstance()
    fun addName(user: User, id:String) {
        if (id != null) {
            db.collection("Users").document(id).set(user)
            //db.collection("Parties").document(id).collection("Participants").document().set(user)
        }

    }
    fun getUser(id:String): UserLiveDataPrueba {
        /*reference.child(id).get().addOnSuccessListener {
            if(it.exists()){
                this.value?.email= it.child("email").value as String?
                this.value?.name= it.child("name").value as String?
        }
    }.addOnFailureListener{
            this.value?.email="ssss"
            this.value?.name="daf"
        }*/
        db.collection("Users").document(id).get().addOnSuccessListener { documents->
            value=documents.toObject(User::class.java)
        }
        return this
    }}