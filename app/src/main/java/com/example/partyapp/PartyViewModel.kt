package com.example.partyapp

import androidx.lifecycle.ViewModel

class PartyViewModel: ViewModel()  {
    private val Parties = PartiesLiveData()
    private var user =UserLiveData()
    fun addParty(party: Party) {
        Parties.addNote(party)
    }
    fun getUser(id:String):UserLiveData{
      return  user.getUser(id)
    }
    fun addUser(newUser:User){
        //user.addName(newUser)
    }
    fun getUserLiveData():UserLiveData{

        return user
    }

}