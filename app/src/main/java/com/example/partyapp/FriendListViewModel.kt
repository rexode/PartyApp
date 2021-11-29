package com.example.partyapp

import androidx.lifecycle.ViewModel

class FriendListViewModel : ViewModel() {

    private  val users = UserLiveData()

    fun addOrChangeList(list: FriendsList, id: String)
    {
        users.addListOrUpdate(list,id)
    }

    fun getList(id: String):MutableList<User>?
    {
        return users.getFriendsList(id)
    }

    fun getUserData():UserLiveData
    {
        return  users
    }

    fun addFriendToList(id: String, email: String)
    {
        users.addFriendToList(id,email)
    }

    fun getFriendsListData(): UserLiveData
    {
        return users
    }
}