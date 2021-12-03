package com.example.partyapp.friendlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp.parties.AllParties
import com.example.partyapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.friends_list_layout.*
import kotlinx.android.synthetic.main.friends_list_layout.view.*

/*
This is not intended as a User space, this is a simple popup which shows your friends, my final vision
for it is to simply show active users(activity status set manually by the user), since we arent keeping
track of that yet for now this will be a simple list
 */

class FriendsListDialogFragment(var Activity: AllParties) : DialogFragment() {

    private lateinit var referance: DatabaseReference
    private lateinit var viewmodel: FriendListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var lview: View = inflater.inflate(R.layout.friends_list_layout, container, false)
        referance = FirebaseDatabase.getInstance().getReference("FriendsLists")
        viewmodel = ViewModelProvider(this).get(FriendListViewModel::class.java)



        lview.textView_user_name_fl.text = Activity.getName()
        lview.recyclerView_friends_list.layoutManager = LinearLayoutManager(Activity)

        button_add_friend.setOnClickListener {
            flist_enter_email.text.clear()
        }





        return lview
    }
}