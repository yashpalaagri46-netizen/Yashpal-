package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GradientCard
import com.example.ui.components.ProgressBarWithGlow
import com.example.ui.components.SearchBarHeader
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val plans by viewModel.studyPlans.collectAsState()
    val testResults by viewModel.testResults.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val completedPlansCount = plans.count { it.isCompleted }
    val totalPlansCount = plans.size
    val progressFraction = if (totalPlansCount > 0) completedPlansCount.toFloat() / totalPlansCount else 0.45f
    val progressPercent = (progressFraction * 100).toInt()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
    ) {
        // Search Bar
        item {
            SearchBarHeader(
                query = searchQuery,
                onQueryChange = { q ->
                    viewModel.updateSearchQuery(q)
                    if (q.isNotBlank()) {
                        val lower = q.lowercase()
                        when {
                            lower.contains("book") || lower.contains("ncert") -> onNavigate(Screen.Books.route)
                            lower.contains("ai") || lower.contains("doubt") -> onNavigate(Screen.AI.route)
                            lower.contains("test") || lower.contains("mock") -> onNavigate(Screen.Tests.route)
                            lower.contains("dpp") -> onNavigate(Screen.DPP.route)
                            lower.contains("video") || lower.contains("lecture") -> onNavigate(Screen.Videos.route)
                            lower.contains("plan") || lower.contains("target") -> onNavigate(Screen.Planner.route)
                            lower.contains("theme") -> onNavigate(Screen.Themes.route)
                        }
                    }
                }
            )
        }

        // HERO BANNER
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_hero_card")
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.Black.copy(alpha = 0.25f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = "🚀", fontSize = 14.sp)
                            Text(
                                text = "DREAM • STUDY • PRACTICE • CRACK",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )
                        }

                        Text(
                            text = "Mission Lakshya",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Text(
                            text = "NEET 2027 Aspirant Portal",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFFFBBF24),
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "NEET preparation के लिए आपका complete digital study platform. NCERT formulas, MCQs, mock tests और AI Doubt Solver.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { onNavigate(Screen.Books.route) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("start_learning_button")
                            ) {
                                Text(
                                    text = "🚀 Start Learning",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            FilledTonalButton(
                                onClick = { onNavigate(Screen.AI.route) },
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = Color.Black.copy(alpha = 0.35f),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("ask_ai_button")
                            ) {
                                Text(
                                    text = "🤖 Ask AI",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }

        // QUICK STATS STRIP
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    icon = "🔥",
                    title = "Streak",
                    value = "${userProfile?.streakDays ?: 5} Days",
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.Performance.route) }
                )
                StatCard(
                    icon = "📝",
                    title = "Tests Done",
                    value = "${testResults.size}",
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.Tests.route) }
                )
                StatCard(
                    icon = "🎯",
                    title = "Target",
                    value = "${userProfile?.targetScore ?: 700}/720",
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.Profile.route) }
                )
            }
        }

        // EXPLORE GRID
        item {
            Text(
                text = "🚀 Explore Mission Lakshya",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "अपनी NEET preparation एक ही जगह से शुरू करें।",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        // GRID ITEMS
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCardItem(
                        icon = "📚",
                        title = "Books & Notes",
                        subtitle = "Physics, Chem & Bio NCERT",
                        route = Screen.Books.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCardItem(
                        icon = "▶️",
                        title = "Video Lectures",
                        subtitle = "Chapter-wise One-Shots",
                        route = Screen.Videos.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCardItem(
                        icon = "🤖",
                        title = "AI Doubt Solver",
                        subtitle = "Step-by-step solutions",
                        route = Screen.AI.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCardItem(
                        icon = "📝",
                        title = "NEET Tests",
                        subtitle = "Full Mock & Chapter Tests",
                        route = Screen.Tests.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCardItem(
                        icon = "📋",
                        title = "DPP Practice",
                        subtitle = "Daily Practice Problems",
                        route = Screen.DPP.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCardItem(
                        icon = "📅",
                        title = "Study Planner",
                        subtitle = "Daily & Weekly Targets",
                        route = Screen.Planner.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCardItem(
                        icon = "🔄",
                        title = "Smart Revision",
                        subtitle = "Spaced repetition & bookmarks",
                        route = Screen.Revision.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCardItem(
                        icon = "📈",
                        title = "Performance",
                        subtitle = "Score & accuracy analysis",
                        route = Screen.Performance.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCardItem(
                        icon = "🌐",
                        title = "Study Websites",
                        subtitle = "11 Curated NEET Portals",
                        route = Screen.Websites.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCardItem(
                        icon = "🎨",
                        title = "35 Themes",
                        subtitle = "Dark, OLED, Neon & Colors",
                        route = Screen.Themes.route,
                        onNavigate = onNavigate,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // PROGRESS BOX
        item {
            GradientCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "🔥 Daily Study Progress",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = "$completedPlansCount of $totalPlansCount daily targets completed",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Text(
                        text = "$progressPercent%",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                ProgressBarWithGlow(progress = progressFraction)
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: String,
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
private fun FeatureCardItem(
    icon: String,
    title: String,
    subtitle: String,
    route: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigate(route) }
            .testTag("feature_card_${route}")
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 2
            )
        }
    }
}
