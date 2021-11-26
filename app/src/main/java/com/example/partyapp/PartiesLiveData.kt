package com.example.partyapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.nio.file.Files.find

class PartiesLiveData: MutableLiveData<MutableList<Party>>() {
    private var reference: DatabaseReference
    init {
        // Initialize LiveData with empty list
        // Connect to database and create a reference to "notes" node
        val database = Firebase
            .database("https://partyapp-4386a-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)
        reference = database.getReference("Parties")


    }
    fun addNote(party:Party) {
        // Get a new unique key from database
        val uid: String? = reference.push().key
        if (uid != null) {
            // Write new value to database under path /notes/$uid
            reference.child(uid).setValue(party)
            // We don't need to handle data or UI changes here,
            // because once data is changes onDataChange() from getNotes() will be called
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
                            .also { note -> note.uid = it.key.toString() }
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