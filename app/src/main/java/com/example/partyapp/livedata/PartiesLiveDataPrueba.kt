package com.example.partyapp.livedata

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.partyapp.parties.Party
import com.google.firebase.firestore.FirebaseFirestore

class PartiesLiveDataPrueba: MutableLiveData<MutableList<Party>>() {
    private var db=FirebaseFirestore.getInstance()



//    Party("error","error","error","error")

    fun findParty(id:String?): PartiesLiveDataPrueba {
        /*getParties().observe(context,{list->
            list.forEach { if (it.uid.equals(id)){
                var overlay=  PartyInfo()
                var args : Bundle = Bundle()
                args.putString("name",it.name)
                args.putString("time",it.time)
                args.putString("date",it.date)
                args.putString("location",it.location)
                args.putString("additionalInfo",it.AditionalInfo)
                overlay.arguments = args
                overlay.show(fragmentManager,
                    "partyOverlay")

            }
            }

        })*/
        db.collection("Parties").whereEqualTo("uid",id).addSnapshotListener{
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

    fun addParty(party: Party):String{
        val id=db.collection("Parties").document().id
        party.uid=id
        db.collection("Parties").document(id).set(party)
        return id
    }
    fun updateNote(party: Party) {
        if (party.uid != null) {
            // Update note under path /notes/$uid
        }
    }
    fun deleteNote(note: Party) {
        if (note.uid != null) {
            // Delete note under path /notes/$uid
        }
    }
    fun getParties(): PartiesLiveDataPrueba {
        /*db.collection("Parties").get().addOnSuccessListener{parties->
            var partiesList = mutableListOf<Party>()
            for(party in parties){
                partiesList.add(party.toObject(Party::class.java))
            }

        }
            return this*/

        db.collection("Parties").orderBy("date").addSnapshotListener{
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
    fun getUserParties(id:String):PartiesLiveDataPrueba{
        db.collection("Parties").orderBy("date").whereEqualTo("creatorId",id).addSnapshotListener{
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