package com.example.studenthandbookapp

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfile : AppCompatActivity() {
    // Firebase components
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    // UI components
    private lateinit var profileImage: ImageView
    private lateinit var fullNameEditText: EditText
    private lateinit var studentNumberEditText: EditText
    private lateinit var courseEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactNumberEditText: EditText
    private lateinit var birthdateEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var changeProfile: TextView
    private lateinit var topAppBar: MaterialToolbar

    private var imageUri: Uri? = null
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            Glide.with(this)
                .load(it)
                .circleCrop()
                .into(profileImage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        // Initialize UI components
        initializeViews()
        setupTopAppBar()
        setupClickListeners()
        loadUserData()
    }

    private fun setupTopAppBar() {
        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        topAppBar.setNavigationOnClickListener {
            finish() // Handle back button press
        }
    }

    private fun initializeViews() {
        profileImage = findViewById(R.id.profileImage)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        studentNumberEditText = findViewById(R.id.studentNumberEditText)
        courseEditText = findViewById(R.id.courseEditText)
        emailEditText = findViewById(R.id.emailEditText)
        contactNumberEditText = findViewById(R.id.contactNumberEditText)
        birthdateEditText = findViewById(R.id.birthdateEditText)
        saveButton = findViewById(R.id.saveButton)
        changePasswordButton = findViewById(R.id.changePasswordButton)
        changeProfile = findViewById(R.id.tvProfilePic)
    }

    private fun setupClickListeners() {
        changeProfile.setOnClickListener { openImageChooser() }
        profileImage.setOnClickListener { openImageChooser() }

        birthdateEditText.setOnClickListener { showDatePickerDialog() }
        saveButton.setOnClickListener { saveProfile() }
        changePasswordButton.setOnClickListener {
            Toast.makeText(this, "Password change functionality will be implemented here", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImageChooser() {
        pickImage.launch("image/*")
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, day)
                }
                birthdateEditText.setText(SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun loadUserData() {
        auth.currentUser?.let { user ->
            emailEditText.setText(user.email)

            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        fullNameEditText.setText(document.getString("fullName"))
                        studentNumberEditText.setText(document.getString("studentNumber"))
                        courseEditText.setText(document.getString("course"))
                        contactNumberEditText.setText(document.getString("contactNumber"))
                        birthdateEditText.setText(document.getString("birthdate"))

                        document.getString("profileImageUrl")?.takeIf { it.isNotEmpty() }?.let { imageUrl ->
                            Glide.with(this)
                                .load(imageUrl)
                                .circleCrop()
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .into(profileImage)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveProfile() {
        val user = auth.currentUser ?: run {
            Toast.makeText(this, "Not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        val userData = hashMapOf(
            "fullName" to fullNameEditText.text.toString(),
            "studentNumber" to studentNumberEditText.text.toString(),
            "course" to courseEditText.text.toString(),
            "contactNumber" to contactNumberEditText.text.toString(),
            "birthdate" to birthdateEditText.text.toString()
        )

        db.collection("users").document(user.uid)
            .update(userData as Map<String, Any>)
            .addOnSuccessListener {
                if (imageUri != null) {
                    uploadProfileImage(user.uid)
                } else {
                    updateEmailIfChanged(user)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateEmailIfChanged(user: FirebaseUser) {
        val newEmail = emailEditText.text.toString()
        if (newEmail != user.email) {
            user.updateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        db.collection("users").document(user.uid)
                            .update("email", newEmail)
                            .addOnSuccessListener { showSuccessAndFinish() }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Profile updated but email update failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Email update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            showSuccessAndFinish()
        }
    }

    private fun uploadProfileImage(userId: String) {
        imageUri?.let { uri ->
            val progressDialog = ProgressDialog(this).apply {
                setMessage("Uploading image...")
                setCancelable(false)
                show()
            }

            val storageRef = storage.reference.child("profile_images/$userId/profile_${System.currentTimeMillis()}.jpg")
            storageRef.putFile(uri)
                .addOnProgressListener { snapshot ->
                    progressDialog.setMessage("Uploading ${(100.0 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt()}%")
                }
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        progressDialog.dismiss()
                        updateProfileImageUrl(downloadUri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun updateProfileImageUrl(imageUrl: String) {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId)
                .update("profileImageUrl", imageUrl)
                .addOnSuccessListener {
                    Glide.with(this)
                        .load(imageUrl)
                        .circleCrop()
                        .into(profileImage)
                    updateEmailIfChanged(auth.currentUser!!)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showSuccessAndFinish() {
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}