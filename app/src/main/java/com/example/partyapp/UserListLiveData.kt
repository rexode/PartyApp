package com.example.partyapp

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class UserListLiveData: MutableLiveData<MutableList<User>>() {
    private var db= FirebaseFirestore.getInstance()
    fun addFollower(user:User,id:String) {

            db.collection("Parties").document(id).collection("participants").add(user)

    }
}