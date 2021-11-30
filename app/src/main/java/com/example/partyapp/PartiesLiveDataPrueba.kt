package com.example.partyapp

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.all_partys_layout.*
import java.nio.file.Files.find

class PartiesLiveDataPrueba: MutableLiveData<MutableList<Party>>() {
    private var db=FirebaseFirestore.getInstance()
    private var reference: DatabaseReference
    init {
        reference = FirebaseDatabase.getInstance().getReference("Parties")
        //db.collection("Parties")


    }

//    Party("error","error","error","error")

    fun findParty(id:String?,context: LifecycleOwner,fragmentManager: FragmentManager) {
        db.collection("Parties").addSnapshotListener{
                snapshot,e->
            if(e!=null){
                Log.w(TAG,"Listen faile",e)
            }
            if(snapshot!=null){
                var document = snapshot.documents
                document.forEach{
                    val party=it.toObject(Party::class.java)
                    if(party!=null && party.uid.equals(id)){
                        var overlay=  PartyInfo()
                        var args : Bundle = Bundle()
                        args.putString("name",party.name)
                        args.putString("time",party.time)
                        args.putString("date",party.date)
                        args.putString("location",party.location)
                        args.putString("additionalInfo",party.AditionalInfo)
                        overlay.arguments = args
                        overlay.show(fragmentManager,
                            "partyOverlay")
                    }
                }
            }

        }
    }

    fun addParty(party:Party) {
        val id=db.collection("participants").document().id
        party.uid=id
        db.collection("Parties").document(id).set(party)
        val string="poa"
        val user=User(string)
        db.collection("Parties").document(id).collection("Participants").document().set(user)


    }
    fun updateNote(party:Party) {
        if (party.uid != null) {
            // Update note under path /notes/$uid
            reference.child(party.uid!!).updateChildren(party.toMap())
        }
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


}