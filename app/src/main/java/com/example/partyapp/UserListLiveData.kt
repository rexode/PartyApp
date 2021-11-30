package com.example.partyapp

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class UserListLiveData: MutableLiveData<MutableList<User>>() {
    private var db= FirebaseFirestore.getInstance()
    fun addFollower(user:User,id:String) {

            db.collection("Parties").document(id).collection("participants").add(user)

    }
    fun getParticipants(partieId:String ):UserListLiveData{
        /*db.collection("Parties").get().addOnSuccessListener{parties->
            var partiesList = mutableListOf<Party>()
            for(party in parties){
                partiesList.add(party.toObject(Party::class.java))
            }

        }
            return this*/

        db.collection("Parties").document(partieId).collection("participants").addSnapshotListener{
                snapshot,e->
            if(e!=null){
                Log.w(ContentValues.TAG,"Listen faile",e)
            }
            if(snapshot!=null){
                var userList = mutableListOf<User>()
                var document = snapshot.documents
                document.forEach{
                    val user=it.toObject(User::class.java)
                    if(user!=null){
                        userList.add(user)
                    }
                }
                value=userList
            }

        }
        return this
    }
}