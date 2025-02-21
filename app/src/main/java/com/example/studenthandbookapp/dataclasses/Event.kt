package com.example.studenthandbookapp.dataclasses

import com.google.firebase.Timestamp

//should have the same fields as the ones in Firestore
data class Event(
    var title: String = "",
    var date: Timestamp? = null, // Using Timestamp instead of String
    var description: String = "",
    var location: String = ""
)