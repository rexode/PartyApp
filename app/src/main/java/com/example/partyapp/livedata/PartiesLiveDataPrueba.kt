package com.example.partyapp.livedata

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.partyapp.parties.Party
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class PartiesLiveDataPrueba : MutableLiveData<MutableList<Party>>() {
    private var db = FirebaseFirestore.getInstance()
    fun findParty(id: String?): PartiesLiveDataPrueba {

        db.collection("Parties").whereEqualTo("uid", id).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen faile", e)
            }

            if (snapshot != null) {
                var partiesList = mutableListOf<Party>()
                var document = snapshot.documents
                document.forEach {
                    val party = it.toObject(Party::class.java)
                    if (party != null) {
                        partiesList.add(party)
                    }
                }
                value = partiesList
            }
        }

        return this
    }

    fun addParty(party: Party): String {
        val id = db.collection("Parties").document().id
        party.uid = id
        db.collection("Parties").document(id).set(party)
        return id
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getParties(): PartiesLiveDataPrueba {

        val sdf = SimpleDateFormat("dd-M-yyyy")
        db.collection("Parties").orderBy("date").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen faile", e)
            }
            if (snapshot != null) {
                var partiesList = mutableListOf<Party>()
                var document = snapshot.documents
                document.forEach {
                    val party = it.toObject(Party::class.java)
                    if (party != null) {

                        partiesList.add(party)
                    }
                }
                value = partiesList
            }

        }
        return this
    }

    fun getUserParties(id: String): PartiesLiveDataPrueba {
        db.collection("Parties").orderBy("date").whereEqualTo("creatorId", id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen faile", e)
                }
                if (snapshot != null) {
                    var partiesList = mutableListOf<Party>()
                    var document = snapshot.documents
                    document.forEach {
                        val party = it.toObject(Party::class.java)
                        if (party != null) {
                            partiesList.add(party)
                        }
                    }
                    value = partiesList
                }

            }

        return this
    }

    fun getParty(id: String) {
        val party: Party? = value?.find { it.uid == id }

    }

    fun deleteParty(id: String) {
        db.collection("Parties").whereEqualTo("uid", id).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen faile", e)
            }
            if (snapshot != null) {
                var document = snapshot.documents
                document.forEach {
                    val party = it.toObject(Party::class.java)
                    it.reference.delete()
                }
            }
        }
    }


}