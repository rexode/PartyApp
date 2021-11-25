package com.example.partyapp

import androidx.lifecycle.ViewModel

class PartyViewModel: ViewModel()  {
    private val Parties = PartiesLiveData()
    fun addParty(party: Party) {
        Parties.addNote(party)
    }

}