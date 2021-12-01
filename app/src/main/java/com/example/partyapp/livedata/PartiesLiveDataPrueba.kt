package com.example.partyapp.livedata

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.partyapp.parties.Party
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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


    @RequiresApi(Build.VERSION_CODES.O)
    fun getParties(): PartiesLiveDataPrueba {
        /*db.collection("Parties").get().addOnSuccessListener{parties->
            var partiesList = mutableListOf<Party>()
            for(party in parties){
                partiesList.add(party.toObject(Party::class.java))
            }

        }
            return this*/
        val sdf = SimpleDateFormat("dd-M-yyyy")
        //val currentDate =LocalDate.parse(sdf.format(Date()), DateTimeFormatter.ISO_DATE)
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
    fun deleteParty(id:String){
        db.collection("Parties").whereEqualTo("uid",id).addSnapshotListener{
                snapshot,e->
            if(e!=null){
                Log.w(TAG,"Listen faile",e)
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