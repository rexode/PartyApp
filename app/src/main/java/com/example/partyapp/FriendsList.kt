package com.example.partyapp

data class FriendsList (var user: String? = null, var friends:MutableList<User>? = null){




    fun toMap(): Map<String, Any?>
    {
        return mapOf(
            "user" to user,
            "friends" to friends,
        )
    }

}