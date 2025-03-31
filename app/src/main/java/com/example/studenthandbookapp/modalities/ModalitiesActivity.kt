package com.example.studenthandbookapp.modalities

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar

class ModalitiesActivity : AppCompatActivity() {
    private lateinit var linearLayout: LinearLayout
    private lateinit var topAppBar: MaterialToolbar

    // Listener for back stack changes
    private val backStackListener = androidx.lifecycle.Observer<Int> { count ->
        if (count == 0) {
            showOtherComponents()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modalities)

        initializeViews()
        setupTopAppBar()
        setupButtonListeners()

        // Setup back stack listener using LiveData
        supportFragmentManager.backStackEntryCountLiveData.observe(this, backStackListener)
    }

    private fun initializeViews() {
        linearLayout = findViewById(R.id.linearlayout)
        topAppBar = findViewById(R.id.topAppBar)
    }

    private fun setupTopAppBar() {
        topAppBar.setNavigationOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        }
    }

    private fun setupButtonListeners() {
        findViewById<ImageButton>(R.id.btnFlex24).setOnClickListener {
            fragmentBeginTransaction(Flex24Fragment())
        }

        findViewById<ImageButton>(R.id.btnFlexRemote).setOnClickListener {
            fragmentBeginTransaction(FlexRemoteFragment())
        }
    }

    private fun fragmentBeginTransaction(fragment: Fragment) {
        hideOtherComponents()
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun hideOtherComponents() {
        linearLayout.visibility = View.GONE
    }

    private fun showOtherComponents() {
        linearLayout.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        // Proper cleanup
        supportFragmentManager.backStackEntryCountLiveData.removeObserver(backStackListener)
        super.onDestroy()
    }
}

// Extension property to observe back stack count changes
val androidx.fragment.app.FragmentManager.backStackEntryCountLiveData
    get() = object : androidx.lifecycle.LiveData<Int>() {
        private val listener = FragmentManager.OnBackStackChangedListener {
            postValue(backStackEntryCount)
        }

        override fun onActive() {
            super.onActive()
            addOnBackStackChangedListener(listener)
            postValue(backStackEntryCount)
        }

        override fun onInactive() {
            super.onInactive()
            removeOnBackStackChangedListener(listener)
        }
    }