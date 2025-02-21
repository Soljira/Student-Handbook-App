package com.example.studenthandbookapp.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.example.studenthandbookapp.dataclasses.Event
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.*

// ALL FIREBASE-RELATED REUSABLE CODE IN ONE PLACE BERI NICE
object FirestoreFunctions {

    private val db = FirebaseFirestore.getInstance()

    /**
     * General Utility
     */

    fun <T> getAllDocumentsFromCollection(
        collectionName: String,
        objectClass: Class<T>,
        onComplete: (List<T>?) -> Unit
    ) {
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentList = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(objectClass)
                    }
                    onComplete(documentList)
                } else {
                    // Collection exists PERO WALANG LAMAN
                    onComplete(emptyList())
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching documents from $collectionName: ${e.message}")
                onComplete(null)
            }
    }

    fun <T> getAllDocumentsWithIds(
        collectionName: String,
        objectClass: Class<T>,
        onComplete: (List<Pair<String, T>>?) -> Unit
    ) {
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentList = querySnapshot.documents.mapNotNull { document ->
                        val obj = document.toObject(objectClass)
                        if (obj != null) Pair(document.id, obj) else null
                    }
                    onComplete(documentList)
                } else {
                    // Collection exists but is empty
                    onComplete(emptyList())
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching documents from $collectionName: ${e.message}")
                onComplete(null)
            }
    }

    fun saveEventToFirestore(context: Context, eventType: String, event: Event, onEventAdded: () -> Unit) {
        db.collection(eventType)
            .add(event)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    context,
                    "Event added with ID: ${documentReference.id}",
                    Toast.LENGTH_SHORT
                ).show()
                onEventAdded() // Notify that an event has been added
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error adding event: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun getEventById(eventType: String, eventId: String, onComplete: (Event?) -> Unit) {
        db.collection(eventType).document(eventId)
            .get()
            .addOnSuccessListener { document: DocumentSnapshot ->
                if (document.exists()) {
                    val event = document.toObject(Event::class.java)
                    onComplete(event)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching event: ${e.message}")
                onComplete(null)
            }
    }

    fun setEventObjectById(eventType: String, eventId: String, onComplete: (Event?) -> Unit) {
        getEventById(eventType, eventId) { event ->
            if (event != null) {
                onComplete(event)
            } else {
                println("Event not found or an error occurred.")
                onComplete(null)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatTimestampToDateString(timestamp: Timestamp?): String {
        return if (timestamp != null) {
            val sdf = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
            val date = timestamp.toDate()
            sdf.format(date)
        } else {
            "No date available"
        }
    }

    /***********************************************
     ***********************************************
     ***********************************************
     ***********************************************/

    /**
     * Calendar functions
     */

    fun listenForEventChanges(eventType: String, onEventUpdate: (List<Event>) -> Unit) {
        db.collection(eventType)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    println("Error listening for event changes: ${error.message}")
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val events = snapshot.documents.mapNotNull { it.toObject(Event::class.java) }
                    onEventUpdate(events) // Notify the calendar of the updated events
                }
            }
    }
}
