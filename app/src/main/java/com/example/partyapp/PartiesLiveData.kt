package com.example.partyapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.nio.file.Files.find

class PartiesLiveData: MutableLiveData<MutableList<Party>>() {
    private var reference: DatabaseReference

    init {
        reference = FirebaseDatabase.getInstance().getReference("Parties")



    }

    fun findParty(id:String?): Party{
        var resParty= Party("123","21312","12312","garching")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Populate a list of notes from database
                snapshot.children.forEach {
                    if (it != null) {
                        val party = it.getValue<Party>()!!
                            .also { party -> party.uid = id }
                        resParty=it.getValue<Party>()!!
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
            }
        })
        return resParty
    }

    fun addParty(party:Party) {
        val uid: String? = reference.push().key
        //val partyA=Party("a","B","C","D","E")
        if (uid != null) {
            reference.child(uid).setValue(party)

            reference.child(uid).child("Participants").setValue("participant1")
            reference.child(uid).child("Participants").setValue("participant2")
            reference.child(uid).child("Participants").setValue("participant3")

        }

    }
    fun updateNote(party:Party) {
        if (party.uid != null) {
            // Update note under path /notes/$uid
        }
    }
    fun deleteNote(note: Party) {
        if (note.uid != null) {
            // Delete note under path /notes/$uid
            reference.child(note.uid!!).removeValue()
        }
    }
    fun getParties():PartiesLiveData{
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Populate a list of notes from database
                var tmpNotesList = mutableListOf<Party>()
                snapshot.children.forEach {
                    if (it != null) {
                        val party = it.getValue<Party>()!!
                            .also { party -> party.uid = it.key.toString() }
                        tmpNotesList.add(party)
                    }
                }

                // Update data in LiveData
                value = tmpNotesList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
            }
        })
            return this
        }
    fun getParty(id:String){
        val party: Party? = value?.find { it.uid == id }

    }


}