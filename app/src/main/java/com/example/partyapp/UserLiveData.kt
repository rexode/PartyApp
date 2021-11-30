package com.example.partyapp

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserLiveData(): LiveData<User>(){
    private var reference: DatabaseReference
    private var fStore: FirebaseFirestore
    init {
        reference = FirebaseDatabase.getInstance().getReference("Users")
        fStore = FirebaseFirestore.getInstance()
    }



    fun addName(user:User,id:String) {
        if (id != null) {
            reference.child(id).setValue(user)
            reference.child(id).child("friendsList")
        }

    }
    fun updateName(user:User,id:String) {
        if (id != null) {
            // Update note under path /notes/$uid
            reference.child(id).updateChildren(user.toMap())
        }

    }
    fun getUser(id:String):UserLiveData{
            /*reference.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    this.value?.email= it.child("email").value as String?
                    this.value?.name= it.child("name").value as String?
            }
        }.addOnFailureListener{
                this.value?.email="ssss"
                this.value?.name="daf"
            }*/
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var newUser:User
                snapshot.children.forEach {
                    if (it != null) {
                        val user = it.getValue<User>()!!
                            .also { note -> if(it.key ==id){
                                newUser=User(note.email,note.name)
                                value=newUser
                            }                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        return this
    }

    //==========================FireStore Part============================================

    fun getFriendsList(id: String) : MutableList<User>?
    {

        var a : MutableList<User> = emptyList<User>() as MutableList<User>

        fStore.collection("Users").document(id).collection("friends").get()
            .addOnSuccessListener { result ->
                for(document in result)
                {
                    a.add(User(document.get("email") as String?, document.get("name") as String?))
                    Log.d(TAG,"${document.id} read")
                }
            }
            .addOnFailureListener{
                exception -> Log.d(TAG,"Error getting the doc",exception)
            }
        return a
    }

    fun addFriendToList(id: String, email: String)
    {
        var key = findIdByEmail(email)
        var name = ""
        var mail = ""
        // immedietly gets the name and mail of the user whos key we got from findIdByEmaul
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var newUser:User
                snapshot.children.forEach {
                    if (it != null) {
                        val user = it.getValue<User>()!!
                            .also { note -> if(it.key == key)
                                name = note.name.toString()
                                mail = note.email.toString()
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        val user = hashMapOf(
            "name" to name,
            "email" to mail,
        )

        fStore.collection("Users").document(id).collection("friends").document(key)
            .set(user)
            .addOnSuccessListener { Log.d(TAG,"Document added successfully") }
            .addOnFailureListener{e -> Log.w(TAG,"Error while creating document")}

    }

    fun addListOrUpdate(flist : FriendsList, id: String)
    {
        for(i in flist.friends!!)
        {
            val user = hashMapOf(
                "name" to i.name,
                "email" to i.email,
            )

            fStore.collection("Users").document(id).collection("friends").document(findIdByEmail(i.email))
                .set(user)
                .addOnSuccessListener { Log.d(TAG,"Document added successfully") }
                .addOnFailureListener{e -> Log.w(TAG,"Error while creating document")}
        }
    }

    fun getFriendsLiveData(id: String)
    {

    }




    fun findIdByEmail(email: String?) : String
    {
        var key = ""
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var newUser:User
                snapshot.children.forEach {
                    if (it != null) {
                        val user = it.getValue<User>()!!
                            .also { note ->
                                if(note.email == email)
                                    key = it.key.toString()
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        return key
    }


}