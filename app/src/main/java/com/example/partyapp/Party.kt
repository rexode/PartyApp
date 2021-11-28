package com.example.partyapp

class Party(var name :String?=null,var time:String?=null,var location:String?=null,var AditionalInfo:String?=null) {
    var uid: String? = null // Unique note's key from database

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "location" to location
        )
}}