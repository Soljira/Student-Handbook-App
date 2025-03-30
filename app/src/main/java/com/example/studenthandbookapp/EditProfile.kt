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
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.example.studenthandbookapp.home.Home
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.core.view.get
import androidx.core.view.size
import com.google.firebase.auth.FirebaseUser

@Suppress("DEPRECATION")
class EditProfile : AppCompatActivity() {
    // Navigation components
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var topAppBar: MaterialToolbar

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

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize navigation components
        initializeNavigationStuff()

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        // Initialize UI components
        initializeViews()
        setupClickListeners()
        loadUserData()
    }

    override fun onResume() {
        super.onResume()
        // Ensure no bottom nav item is selected in this nested activity
        unselectBottomNavIcon(bottomNavigationView)
    }

    private fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        // Set up navigation components
        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Edit Profile")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        // Initially unselect all bottom nav items
        unselectBottomNavIcon(bottomNavigationView)
    }

    private fun unselectBottomNavIcon(bottomNav: BottomNavigationView) {
        bottomNav.menu.setGroupCheckable(0, true, false)
        for (i in 0 until bottomNav.menu.size) {
            bottomNav.menu[i].isChecked = false
        }
        bottomNav.menu.setGroupCheckable(0, true, true)
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
        changeProfile.setOnClickListener {
            openImageChooser()
        }

        profileImage.setOnClickListener {
            openImageChooser()
        }

        birthdateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        saveButton.setOnClickListener {
            saveProfile()
        }

        changePasswordButton.setOnClickListener {
            Toast.makeText(this, "Password change functionality will be implemented here", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                birthdateEditText.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Set email from FirebaseAuth first (this is always available)
            emailEditText.setText(currentUser.email)

            // Then load additional data from Firestore
            db.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        fullNameEditText.setText(document.getString("fullName"))
                        studentNumberEditText.setText(document.getString("studentNumber"))
                        courseEditText.setText(document.getString("course"))
                        contactNumberEditText.setText(document.getString("contactNumber"))
                        birthdateEditText.setText(document.getString("birthdate"))

                        // Load profile image
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
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "Not authenticated", Toast.LENGTH_SHORT).show()
            val userData = hashMapOf(
                "fullName" to fullNameEditText.text.toString(),
                "studentNumber" to studentNumberEditText.text.toString(),
                "course" to courseEditText.text.toString(),
                "contactNumber" to contactNumberEditText.text.toString(),
                "birthdate" to birthdateEditText.text.toString()
                // Note: We don't include email here as it needs special handling

            )

            // First update Firestore data
            db.collection("users").document(currentUser.uid)
                .update(userData as Map<String, Any>)
                .addOnSuccessListener {
                    // Then handle profile image if changed
                    if (imageUri != null) {
                        uploadProfileImage(currentUser.uid)
                    } else {
                        // Check if email was changed
                        val newEmail = emailEditText.text.toString()
                        if (newEmail != currentUser.email) {
                            updateUserEmail(currentUser, newEmail)
                        } else {
                            showSuccessAndFinish()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateUserEmail(user: FirebaseUser, newEmail: String) {
        user.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Also update email in Firestore for consistency
                    db.collection("users").document(user.uid)
                        .update("email", newEmail)
                        .addOnSuccessListener {
                            showSuccessAndFinish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Profile updated but email update failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Email update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showSuccessAndFinish() {
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    private fun uploadProfileImage(userId: String) {
        // Ensure user is authenticated
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        // Create reference with proper path structure
        val storageRef = storage.reference.child("profile_images/$userId/profile_${System.currentTimeMillis()}.jpg")

        // Create upload task
        imageUri?.let { uri ->
            // Show progress dialog
            val progressDialog = ProgressDialog(this).apply {
                setMessage("Uploading image...")
                setCancelable(false)
                show()
            }

            // Start upload
            storageRef.putFile(uri)
                .addOnProgressListener { snapshot ->
                    val progress = (100.0 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt()
                    progressDialog.setMessage("Uploading $progress%")
                }
                .addOnSuccessListener { taskSnapshot ->
                    // Get download URL
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        progressDialog.dismiss()
                        updateProfileImageUrl(downloadUri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        } ?: run {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProfileImageUrl(imageUrl: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .update("profileImageUrl", imageUrl)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                // Optional: Refresh profile image display
                Glide.with(this)
                    .load(imageUrl)
                    .circleCrop()
                    .into(profileImage)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    @Deprecated("Deprecated in favor of Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {
            imageUri = data.data
            Glide.with(this)
                .load(imageUri)
                .circleCrop()
                .into(profileImage)
        }
    }
}