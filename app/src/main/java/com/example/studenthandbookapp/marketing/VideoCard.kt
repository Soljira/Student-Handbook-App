package com.example.studenthandbookapp.marketing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.studenthandbookapp.ui.theme.JosefinSans
import com.example.studenthandbookapp.ui.theme.Merriweather

@Composable
fun VideoCard(
    author: String,
    datePosted: String,
    description: String,
    imageRes: Int,
    video: VideoData,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val isLongDescription = description.length > 344
    val displayedText =
        if (expanded || !isLongDescription) description else description.take(344) + "..."

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3A4F24)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .width(350.dp)
                .clickable { onClick() }
        ) {
            Column {
                Image(
                    painter = painterResource(id = video.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Column {
                            Text(
                                text = displayedText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Merriweather,
                                color = Color.White
                            )
                            if (isLongDescription) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = if (expanded) "See Less" else "See More",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFD000),
                                    modifier = Modifier.clickable { expanded = !expanded }
                                )

                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            thickness = 0.5.dp,
                            color = Color.White
                        )

                        Text(
                            text = video.author,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = JosefinSans,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = video.datePosted,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Merriweather,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }
            }
        }
    }
}
