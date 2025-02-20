package com.example.studenthandbookapp.helpers

import android.content.Context
import android.widget.Toast
import com.example.studenthandbookapp.dataclasses.Event
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot


// ALL FIREBASE-RELATED REUSABLE CODE IN ONE PLACE BERI NICE
object FirestoreFunctions {

    /**
     * Events functions
     */

    // Code for saving custom event details to Firestore; not sure kung magagamit to given the time pero lagay ko lang here just in case
    // eventType is either
    // - events_user
    // - events_school
    // - events_national
    fun saveEventToFirestore(context : Context, eventType: String, event: Event) {
        val db = FirebaseFirestore.getInstance()
        db.collection(eventType)
            .add(event)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Event added with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error adding event: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Code for GETTING event details from Firestore
    // This will return an Event data class (using ung ginawa ko kanina na Event object)
    // ung paggawa ng list of Events siguro later na
    // eventType is either
    // - events_user
    // - events_school
    // - events_national
    fun getEventById(eventType : String, eventId: String, onComplete: (Event?) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection(eventType).document(eventId)
            .get()
            .addOnSuccessListener { document: DocumentSnapshot ->
                if (document.exists()) {
                    val event = document.toObject(Event::class.java)
                    onComplete(event) // returns the Event object
                } else {
                    onComplete(null) // No document found
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching event: ${e.message}")
                onComplete(null) // In case of failure
            }
    }

    // This will set an Event object to the data from Firestore
    // i found out that firestore is asynchronous, so habang nagfefetch pa ng data, narurun na ung ibang code, so
    // nagrereturn sya ng blank test minsan kasi hindi pa tapos magfetch ng data
    // eventType is either
    // - events_user
    // - events_school
    // - events_national
    /*
        HOW TO USE SA ACTIVIITIES:
        FirestoreFunctions.setEventObjectById("events_school", eventId) { event ->
            if (event != null) {
                eventTest = event  // eventTest is your empty Event object
            } else {
                println("Event not found.")
            }
        }

        OR (SINCE ERROR CHECK IS ALREADY HANDLED HERE)

        FirestoreFunctions.setEventObjectById("events_school", eventId) { event ->
            eventTest = event  // eventTest is your empty Event object
            title.text = eventTest?.title
        }

     */
    fun setEventObjectById(eventType: String, eventId: String, onComplete: (Event?) -> Unit) {
        getEventById(eventType, eventId) { event ->
            if (event != null) {
                onComplete(event) // returns the Event object
            } else {
                println("Event not found or an error occurred.")
                onComplete(null)
            }
        }
    }



    // fancy divider lol
    /***********************************************
     ***********************************************
     ***********************************************
     ***********************************************/

    /**
     * Calendar functions
     */

}