package com.example.partyapp

import androidx.lifecycle.ViewModel

class FriendListViewModel : ViewModel() {

    private  val users = UserLiveData()
    private val friendsLists = FriendsListLiveData()

    fun addList(list: FriendsList, id: String)
    {
        friendsLists.addList(list,id)
    }

    fun changeList(id: String, list: FriendsList, fid: String)
    {
        friendsLists.addFriendtoList(list,id,fid)
    }

    fun getList(id: String):FriendsListLiveData
    {
        return friendsLists.getFriendsList(id)
    }

    fun getUserData():UserLiveData
    {
        return  users
    }

    fun getFriendsListData(): FriendsListLiveData
    {
        return  friendsLists
    }
}