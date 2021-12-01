package com.example.partyapp.parties

class Party(var name :String?=null,var date:String?=null,var time:String?=null,var location:String?=null,var AditionalInfo:String?=null, var creatorId:String?=null) {

    var uid: String? = null // Unique note's key from database

    //var participants= mutableListOf<String>()

    //fun toMap(): Map<String, Any?> {
     //   return mapOf(
      //      "name" to name,
        //    "location" to location
        //)
//}
}