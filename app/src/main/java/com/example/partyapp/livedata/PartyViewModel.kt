package com.example.partyapp.livedata

import androidx.lifecycle.ViewModel
import com.example.partyapp.parties.Party
import com.example.partyapp.partydetails.User

class PartyViewModel: ViewModel()  {
    private val Partiesprueba = PartiesLiveDataPrueba()
    private val userprueba= UserLiveDataPrueba()
    private val userList= UserListLiveData()


    fun addParty(party: Party, user: User):String {
        //val party=Party("a","B","C","D","E")

        return Partiesprueba.addParty(party)

    }
    fun getUser(id:String): UserLiveDataPrueba {
      return  userprueba.getUser(id)
    }
    fun getParties(): PartiesLiveDataPrueba {
        return Partiesprueba.getParties()
    }
    
    fun getUserLiveData(): UserLiveDataPrueba {

        return userprueba
    }

    fun findParty(id:String?) : PartiesLiveDataPrueba {
         return Partiesprueba.findParty(id)
    }
    fun addParticipants(email:String,id:String,name:String, partyId:String){
        return userList.addFollower(email, id, name, partyId)
    }
    fun getParticipants(partiId:String): UserListLiveData {
       return userList.getParticipants(partiId)

    }
    fun removeParticipant(userId:String,partyId:String){
        userList.removeParticipant(userId,partyId)
    }
    fun addFollowing(email:String,id:String,name:String, userId:String){
        userList.addFollowing(email,id,name,userId)
    }
    fun getUserParties(id:String):PartiesLiveDataPrueba{
        return getParties().getUserParties(id)
    }

}