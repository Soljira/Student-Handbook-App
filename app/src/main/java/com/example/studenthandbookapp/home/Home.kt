package com.example.studenthandbookapp.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.ui.theme.JosefinSans
import com.example.studenthandbookapp.ui.theme.Merriweather

class Home : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CenterAlignedTopAppBarExample()
        }
    }


    /**
     * FROM: https://developer.android.com/develop/ui/compose/components/app-bars
     */
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun CenterAlignedTopAppBarExample() {

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Home",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = JosefinSans,
                            fontWeight = FontWeight.Normal
                        )
                    },

                    // MENU ICON
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },

                    // PROFILE ICON; WILL LEAD TO PROFILE PAGE
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_person),
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        ) {
            //scroll content goes here!!!!
        }
    }

    @Composable
    fun ScrollContent(innerPadding: PaddingValues) {
        // DI KO PA ALAM ANO ILALAGAY
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            repeat(20) { // Dummy content
                Text("Item $it", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
