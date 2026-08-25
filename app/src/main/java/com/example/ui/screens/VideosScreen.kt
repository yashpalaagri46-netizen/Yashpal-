package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Subject
import com.example.model.VideoLectureItem
import com.example.ui.components.GradientCard
import com.example.ui.components.SubjectBadge
import com.example.ui.viewmodel.MainViewModel

@Composable
fun VideosScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val videos = viewModel.videoLectures
    val context = LocalContext.current
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }

    val filteredVideos = remember(selectedSubject) {
        if (selectedSubject == null) videos else videos.filter { it.subject == selectedSubject }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        item {
            GradientCard {
                Text(
                    text = "▶️ High-Yield NEET Video Lectures",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Curated Hindi & English chapter one-shots from top medical entrance educators.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedSubject == null,
                    onClick = { selectedSubject = null },
                    label = { Text("All Lectures") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedSubject == Subject.PHYSICS,
                    onClick = { selectedSubject = Subject.PHYSICS },
                    label = { Text("⚡ Physics") },
                    modifier = Modifier.weight(1.1f)
                )
                FilterChip(
                    selected = selectedSubject == Subject.CHEMISTRY,
                    onClick = { selectedSubject = Subject.CHEMISTRY },
                    label = { Text("🧪 Chem") },
                    modifier = Modifier.weight(1.1f)
                )
                FilterChip(
                    selected = selectedSubject == Subject.BIOLOGY,
                    onClick = { selectedSubject = Subject.BIOLOGY },
                    label = { Text("🧬 Bio") },
                    modifier = Modifier.weight(1.1f)
                )
            }
        }

        items(filteredVideos, key = { it.id }) { video ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=${Uri.encode(video.youtubeQuery)}"))
                        context.startActivity(intent)
                    }
                    .testTag("video_card_${video.id}")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SubjectBadge(subject = video.subject)

                        Text(
                            text = video.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 2
                        )

                        Text(
                            text = "Channel: ${video.channel} • ⏱️ ${video.duration}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    }
}
