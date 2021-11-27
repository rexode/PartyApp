package com.example.partyapp

import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class FriendsListLiveData : LiveData<FriendsList>() {
    private var referance : DatabaseReference
    init {
        referance = FirebaseDatabase.getInstance().getReference("FriendsLists")
    }

    fun addList(flist: FriendsList, id: String){
        referance.child(id).setValue(flist)
    }

    fun updateList(flist: FriendsList,id: String)
    {
        referance.child(id).updateChildren(flist.toMap())
    }

    fun addFriendtoList(flist: FriendsList,id: String,friendId: String)
    {
        var usData : UserLiveData = UserLiveData()
        flist.friends!!.add(usData.getUser(friendId).value!!)
        updateList(flist,id)
    }

    fun getFriendsList(id: String): FriendsListLiveData
    {
        referance.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var llist: FriendsList
                snapshot.children.forEach{
                    if(it != null)
                    {
                        val tlist = it.getValue<FriendsList>()!!
                            .also { list -> if(it.key == id){
                                llist = FriendsList(list.user,list.friends)
                                value = llist
                            }
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return this

    }
}