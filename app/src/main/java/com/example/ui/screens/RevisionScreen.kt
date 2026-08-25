package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookmarkEntity
import com.example.ui.components.GradientCard
import com.example.ui.viewmodel.MainViewModel

@Composable
fun RevisionScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val bookmarks by viewModel.bookmarks.collectAsState()
    val chapters = viewModel.chapters
    var selectedTab by remember { mutableIntStateOf(0) }

    val flashcards = remember {
        listOf(
            "What is the function of Juxtaglomerular Apparatus (JGA)?" to "Secretes Renin in response to fall in glomerular blood flow/pressure to activate RAAS.",
            "State Lenz's Law in Electromagnetic Induction." to "The polarity of induced emf is such that it tends to produce a current which opposes the change in magnetic flux that produces it.",
            "What are the 3 criteria for aromaticity according to Hückel's Rule?" to "1. Cyclic planar ring, 2. Complete conjugation of π electrons, 3. (4n + 2) π electrons.",
            "In which stage of meiosis do chiasmata become visible?" to "Diplotene stage of Prophase I.",
            "Write the formula for drift velocity of electrons." to "v_d = (e E τ) / m where τ is relaxation time."
        )
    }

    var currentFlashcardIndex by remember { mutableIntStateOf(0) }
    var isFlashcardFlipped by remember { mutableStateOf(false) }

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
                    text = "🔄 Smart Revision & Bookmarks",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Spaced repetition flashcards and saved high-yield questions for fast last-minute recall.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("⚡ Flashcards") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("🔖 Bookmarks (${bookmarks.size})") }
                )
            }
        }

        if (selectedTab == 0) {
            // Flashcard Interactive Box
            item {
                val (question, answer) = flashcards[currentFlashcardIndex]

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clickable { isFlashcardFlipped = !isFlashcardFlipped }
                        .testTag("flashcard_card")
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isFlashcardFlipped) "💡 ANSWER" else "❓ QUESTION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = if (isFlashcardFlipped) answer else question,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "(Tap card to flip 🔄)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            isFlashcardFlipped = false
                            if (currentFlashcardIndex > 0) currentFlashcardIndex--
                            else currentFlashcardIndex = flashcards.size - 1
                        }
                    ) {
                        Text("⬅ Previous")
                    }

                    Text(
                        text = "${currentFlashcardIndex + 1} / ${flashcards.size}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Button(
                        onClick = {
                            isFlashcardFlipped = false
                            if (currentFlashcardIndex < flashcards.size - 1) currentFlashcardIndex++
                            else currentFlashcardIndex = 0
                        }
                    ) {
                        Text("Next ➡")
                    }
                }
            }
        } else {
            // Bookmarks List
            if (bookmarks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No bookmarks saved yet. Star any question or chapter note to see it here!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            items(bookmarks, key = { it.id }) { bookmark ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "${bookmark.subject} • ${bookmark.type}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            IconButton(onClick = { viewModel.deleteBookmark(bookmark) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Text(
                            text = bookmark.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        if (bookmark.subtitle.isNotBlank()) {
                            Text(
                                text = bookmark.subtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = bookmark.content,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    }
}
