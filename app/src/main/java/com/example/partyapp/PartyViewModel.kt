package com.example.partyapp

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class PartyViewModel: ViewModel()  {
    private val Parties = PartiesLiveData()
    private var user =UserLiveData()
    fun addParty(party: Party) {
        Parties.addParty(party)
    }
    fun getUser(id:String):UserLiveData{
      return  user.getUser(id)
    }
    fun getParties():PartiesLiveData{
        return Parties.getParties()
    }
    fun addUser(newUser:User){
        FirebaseAuth.getInstance().currentUser?.let { user.addName(newUser, it.uid) }
    }
    fun getUserLiveData():UserLiveData{

        return user
    }

    fun findParty(id:String?) : Party?{
        return Parties.findParty(id)
    }

}