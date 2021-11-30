package com.example.partyapp

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.nio.file.Files.find

class PartiesLiveDataPrueba: MutableLiveData<MutableList<Party>>() {
    private var db=FirebaseFirestore.getInstance()
    private var reference: DatabaseReference
    init {
        reference = FirebaseDatabase.getInstance().getReference("Parties")
        //db.collection("Parties")


    }
    fun addParty(party:Party) {
        val id=db.collection("participants").document().id
        party.uid=id
        db.collection("Parties").document(id).set(party)
        val string="poa"
        val user=User(string)
        db.collection("Parties").document(id).collection("Participants").document().set(user)


    }

    fun deleteNote(note: Party) {
        if (note.uid != null) {
            // Delete note under path /notes/$uid
            reference.child(note.uid!!).removeValue()
        }
    }
    fun getParties():PartiesLiveDataPrueba{
        /*db.collection("Parties").get().addOnSuccessListener{parties->
            var partiesList = mutableListOf<Party>()
            for(party in parties){
                partiesList.add(party.toObject(Party::class.java))
            }

        }
            return this*/

        db.collection("Parties").addSnapshotListener{
                snapshot,e->
            if(e!=null){
                Log.w(TAG,"Listen faile",e)
            }
            if(snapshot!=null){
                var partiesList = mutableListOf<Party>()
                var document = snapshot.documents
                document.forEach{
                    val party=it.toObject(Party::class.java)
                    if(party!=null){
                        partiesList.add(party)
                    }
                }
                value=partiesList
            }

        }
        return this
    }
    fun getParty(id:String){
        val party: Party? = value?.find { it.uid == id }

    }
    fun findParty(id:String?): Party{
        var resParty= Party(id,"21312","12312","info")
        value?.forEach{
            if(it!=null){
                if(it.uid==id){
                    resParty.uid=id
                    resParty.AditionalInfo=it.AditionalInfo
                    resParty.name=it.name
                    resParty.location=it.location
                    resParty.date=it.date
                    resParty.time=it.time
                    resParty.participants=it.participants
                }
            }
        }
        resParty= value?.get(0)!!
        return resParty
    }


}