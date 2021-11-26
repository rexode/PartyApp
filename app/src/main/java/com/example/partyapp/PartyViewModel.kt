package com.example.partyapp

import androidx.lifecycle.ViewModel

class PartyViewModel: ViewModel()  {
    private val Parties = PartiesLiveData()
    private val user =UserLiveData()
    fun addParty(party: Party) {
        Parties.addNote(party)
    }
    fun getUser(){
        //user.getUSer()
    }
    fun addUser(newUser:User){
        //user.addName(newUser)
    }

}