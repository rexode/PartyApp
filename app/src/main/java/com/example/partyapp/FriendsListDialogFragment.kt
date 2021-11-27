package com.example.partyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.friends_list_layout.*

/*
This is not intended as a User space, this is a simple popup which shows your friends, my final vision
for it is to simply show active users(activity status set manually by the user), since we arent keeping
track of that yet for now this will be a simple list
 */

class FriendsListDialogFragment : DialogFragment() {

    private lateinit var referance: DatabaseReference
    private lateinit var viewmodel : FriendListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var lview : View = inflater.inflate(R.layout.friends_list_layout,container,false)



        return lview
    }
}