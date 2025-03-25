package com.example.studenthandbookapp.marketing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.example.studenthandbookapp.R

@Composable
fun VideoCard(
    description: String,
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
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = video.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { onClick() },
                        modifier = Modifier.size(60.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(Color.White.copy(alpha = 0.6f))                  ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_play),
                            modifier = Modifier
                                .size(50.dp),
                            contentDescription = "Play",
                            tint = Color(0xFF3A4F24).copy(alpha = 0.8f)
                        )
                    }
                }
                Box(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Column {
                            Text(
                                text = displayedText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
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
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = video.datePosted,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
