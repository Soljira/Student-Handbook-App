package com.example.studenthandbookapp.marketing


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.compose.rememberNavController
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class Marketing : AppCompatActivity() {

    //navigation stuff START
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var topAppBar: MaterialToolbar
//navigation stuff END

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketing)

        initializeNavigationStuff()

        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            rememberNavController()
            Uri.EMPTY // Or a default URI

            MarketingScreen()
        }
    }
    // IM SO STUPID
    // THIS FUNCTION ENSURES THAT THE APPROPRIATE ICON IS CHECKED EVEN WHEN YOU PRESS THE BACK BUTTON
    // DO NOT COPY THIS FUNCTION TO NESTED ACTIVITIES BECAUSE IT WILL BREAK THE APP
    // THANKS
    // I WASTED 3 HOURS ON THIS
    override fun onResume() {
        super.onResume()
//        bottomNavigationView.selectedItemId = R.id.nav_profile
        unselectBottomNavIcon(bottomNavigationView)
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Marketing")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        unselectBottomNavIcon(bottomNavigationView)
    }

    // navigation stuff END
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MarketingScreen() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center // Ensures content inside is centered
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centers all items horizontally
        ) {
            item {
                Box(
                    contentAlignment = Alignment.TopCenter, // Center logo horizontally
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Banner Image
                    Image(
                        painter = painterResource(id = R.drawable.marketing_banner),
                        contentDescription = "Marketing Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Overlapping University Logo
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp) // Adjust size as needed
                            .offset(y = 100.dp) // Moves logo up to overlap the banner
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_upang),
                            contentDescription = "University Logo",
                            modifier = Modifier
                                .size(90.dp) // Slightly smaller than the white background
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            item {
                Text(
                    text = "PHINMA—University of Pangasinan ",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                ContactUsSection()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp)) // Add space before the first item
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
            items(videoList) { video ->
                VideoCard(author = video.author,
                    datePosted = video.datePosted,
                    description = video.description,
                    imageRes = video.imageRes,
                    video = video,
                    onClick = {
                        val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                            putExtra("videoUri", video.videoUrl)
                        }
                        context.startActivity(intent)
                    })
            }
            item {
                Spacer(modifier = Modifier.height(16.dp)) // Add space before the first item
            }
        }
    }
}

@Composable
fun ContactUsSection() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        // Contact Us Section
        ExpandableCard(title = "CONTACT US",
            isExpanded = false, // Expanded by default
            onExpand = { }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactDetails(
                    title = "Dagupan Campus",
                    address = "Arellano Street, Dagupan City, 2400, Pangasinan",
                    phoneNumbers = listOf("+63 995-078-5660", "(075) 522-5635", "(075) 522-2496"),
                    email = "info.up@phinmaed.com"
                )

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    thickness = 0.5.dp,
                    color = Color.White
                )
                ContactDetails(
                    title = "Urdaneta Campus",
                    address = "McArthur Highway, Urdaneta City, 2428, Pangasinan",
                    phoneNumbers = listOf("+63 921-211-9138"),
                    email = "info.up@phinmaed.com"
                )

                Spacer(modifier = Modifier.height(18.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    thickness = 0.5.dp,
                    color = Color.White
                )

                // Social Media Section with Clickable Icons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SocialMediaIcon(
                        context = context,
                        iconRes = R.drawable.ic_facebook,
                        url = "https://www.facebook.com/PHINMA.UPang"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SocialMediaIcon(
                        context = context,
                        iconRes = R.drawable.ic_twitter,
                        url = "https://twitter.com/PHINMA_UPang"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SocialMediaIcon(
                        context = context,
                        iconRes = R.drawable.ic_instagram,
                        url = "https://www.instagram.com/phinmaupang"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SocialMediaIcon(
                        context = context,
                        iconRes = R.drawable.ic_youtube,
                        url = "https://www.youtube.com/c/PHINMAEducation"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SocialMediaIcon(context: android.content.Context, iconRes: Int, url: String) {
    Icon(
        tint = Color.White,
        painter = painterResource(id = iconRes),
        contentDescription = "Social Media",
        modifier = Modifier
            .size(24.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
    )
}


@Composable
fun ExpandableCard(
    title: String,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    content: @Composable () -> Unit
) {
    var expandedState by remember { mutableStateOf(isExpanded) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                expandedState = !expandedState
                onExpand()
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A4F24)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f)) // Push title to center

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.weight(5f), // Center the text
                    textAlign = TextAlign.Center
                )

                Icon(
                    painter = painterResource(
                        if (expandedState) R.drawable.ic_expand_less
                        else R.drawable.ic_expand_more
                    ),
                    contentDescription = if (expandedState) "Collapse" else "Expand",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .weight(1f) // Align icon properly
                        .clickable {
                            expandedState = !expandedState
                            onExpand()
                        }
                )
            }
            // Expandable Content
            if (expandedState) {
                content()
            }
        }
    }
}


@Composable
fun ContactDetails(title: String, address: String, phoneNumbers: List<String>, email: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color(0xFFFFD000)
        )

        ContactItem(R.drawable.ic_location, address)
        phoneNumbers.forEach { phone -> ContactItem(R.drawable.ic_phone, phone) }
        ContactItem(R.drawable.ic_email, email)
    }
}

@Composable
fun ContactItem(iconRes: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            tint = Color.White,
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text, color = Color.White)
    }
}

