package com.example.partyapp.livedata

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.partyapp.partydetails.User
import com.google.firebase.firestore.FirebaseFirestore

class UserListLiveData : MutableLiveData<MutableList<User>>() {
    private var db = FirebaseFirestore.getInstance()
    fun addFollower(email: String, id: String, name: String, partyId: String) {

        db.collection("Parties").document(partyId).collection("participants").document(id).set(
            User(email, id, name)
        )

    }

    fun getParticipants(partieId: String): UserListLiveData {


        db.collection("Parties").document(partieId).collection("participants")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen faile", e)
                }
                if (snapshot != null) {
                    var userList = mutableListOf<User>()
                    var document = snapshot.documents
                    document.forEach {
                        val user = it.toObject(User::class.java)
                        if (user != null) {
                            userList.add(user)
                        }
                    }
                    value = userList
                }

            }
        return this
    }

    fun removeParticipant(userId: String, partyId: String) {
        db.collection("Parties").document(partyId).collection("participants").document(userId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun addFollowing(email: String, id: String, name: String, userId: String) {
        if (!id.equals(userId)) {
            db.collection("Users").document(userId).collection("followings").document(id)
                .set(User(email, id, name))
        }
    }

    fun getFollowing(id: String): UserListLiveData {
        db.collection("Users").document(id).collection("followings")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen faile", e)
                }
                if (snapshot != null) {
                    var userList = mutableListOf<User>()
                    var document = snapshot.documents
                    document.forEach {
                        val user = it.toObject(User::class.java)
                        if (user != null) {
                            userList.add(user)
                        }
                    }
                    value = userList
                }
            }
        return this
    }

    fun removeFollowing(userId: String, followingId: String) {
        db.collection("Users").document(userId).collection("followings").document(followingId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}