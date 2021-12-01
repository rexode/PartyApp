package com.example.partyapp.livedata

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.partyapp.parties.Party
import com.example.partyapp.partydetails.User
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
    }
    fun deleteUserParty(userId:String,partyId:String){
        db.collection("Parties").document(partyId).collection("participants").whereEqualTo("uid",userId).addSnapshotListener{
                snapshot,e->
            if(e!=null){
                Log.w(ContentValues.TAG,"Listen failed",e)
            }
            if(snapshot!=null){
                var document = snapshot.documents
                document.forEach{
                    val party=it.toObject(Party::class.java)
                    it.reference.delete()
                }
            }
        }
    }
}
