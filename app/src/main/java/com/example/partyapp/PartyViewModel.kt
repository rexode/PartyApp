package com.example.partyapp

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class PartyViewModel: ViewModel()  {
    private val Parties = PartiesLiveData()
    private var user =UserLiveData()
    private val Partiesprueba = PartiesLiveDataPrueba()
    private val userprueba=UserLiveDataPrueba()


    fun addParty(party: Party) {
        //val party=Party("a","B","C","D","E")

        Partiesprueba.addParty(party)
    }
    fun getUser(id:String):UserLiveDataPrueba{
      return  userprueba.getUser(id)
    }
    fun getParties():PartiesLiveDataPrueba{
        return Partiesprueba.getParties()
    }
    fun addUser(newUser:User){
        FirebaseAuth.getInstance().currentUser?.let { user.addName(newUser, it.uid) }
    }
    fun getUserLiveData():UserLiveDataPrueba{

        return userprueba
    }

    fun findParty(id:String?) : Party?{
        return Parties.findParty(id)
    }

}