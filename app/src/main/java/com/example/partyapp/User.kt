package com.example.partyapp

data class User(var email: String?=null,var name:String?=null,var id:String?=null) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "name" to name
        )
    }

}