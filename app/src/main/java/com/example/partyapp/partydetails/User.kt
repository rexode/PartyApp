package com.example.partyapp.partydetails

data class User(var email: String? = null, var id: String? = null, var name: String? = null) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "id" to id,
            "name" to name

        )
    }

}