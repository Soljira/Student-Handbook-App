package com.example.studenthandbookapp.dataclasses

//should have the same fields as the ones in Firestore
data class Event(
    var title: String = "",
    var date: String = "",  //todo: convert timestamp
    var description: String = "",
    var location: String = ""
)