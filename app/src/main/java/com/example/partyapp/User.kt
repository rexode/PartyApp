package com.example.partyapp
//BEFORE: EMAIL NAME USER -------------------30.11.2021 21:01---->EMAIL ID NAME
data class User(var email: String?=null,var id:String?=null,var name:String?=null) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "id" to id,
            "name" to name

        )
    }

}