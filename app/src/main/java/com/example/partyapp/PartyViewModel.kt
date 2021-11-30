package com.example.partyapp

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class PartyViewModel: ViewModel()  {
    private val Parties = PartiesLiveData()
    private var user =UserLiveData()
    private val Partiesprueba = PartiesLiveDataPrueba()
    private val userprueba=UserLiveDataPrueba()
    private val userList=UserListLiveData()


    fun addParty(party: Party,user:User):String {
        //val party=Party("a","B","C","D","E")

        return Partiesprueba.addParty(party)

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

    fun findParty(id:String?) :PartiesLiveDataPrueba{
         return Partiesprueba.findParty(id)
    }
    fun addParticipants(email:String,id:String,name:String, partyId:String){
        return userList.addFollower(email, id, name, partyId)
    }
    fun getParticipants(partiId:String):UserListLiveData{
       return userList.getParticipants(partiId)

    }
    fun removeParticipant(userId:String,partyId:String){
        userList.removeParticipant(userId,partyId)
    }

}