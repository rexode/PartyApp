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
    fun addParty(party:Party) {
        val uid: String? = reference.push().key
        if (uid != null) {
            reference.child(uid).setValue(party)
        }

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