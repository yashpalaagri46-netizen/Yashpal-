package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Chapter
import com.example.model.Subject
import com.example.ui.components.GradientCard
import com.example.ui.components.SubjectBadge
import com.example.ui.viewmodel.MainViewModel

@Composable
fun BooksAndNotesScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val chapters = viewModel.chapters
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    var expandedChapter by remember { mutableStateOf<Chapter?>(null) }
    val bookmarks by viewModel.bookmarks.collectAsState()

    val filteredChapters = remember(selectedSubject) {
        if (selectedSubject == null) chapters else chapters.filter { it.subject == selectedSubject }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Subject Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedSubject == null,
                onClick = { selectedSubject = null },
                label = { Text("All (PCB)") },
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

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                GradientCard {
                    Text(
                        text = "📖 NCERT NEET Master Notes & Formulas",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "Detailed formula sheets, NCERT summary points, and high-yield chapter notes.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            items(filteredChapters, key = { it.id }) { chapter ->
                val isBookmarked = bookmarks.any { it.title == chapter.title }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandedChapter = if (expandedChapter?.id == chapter.id) null else chapter
                        }
                        .testTag("chapter_card_${chapter.id}")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SubjectBadge(subject = chapter.subject)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (chapter.isHighYield) {
                                    Surface(
                                        color = Color(0xFFFEF3C7),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "⭐ High Yield",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = Color(0xFF92400E),
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        if (isBookmarked) {
                                            val b = bookmarks.firstOrNull { it.title == chapter.title }
                                            if (b != null) viewModel.deleteBookmark(b)
                                        } else {
                                            viewModel.addBookmark(
                                                title = chapter.title,
                                                subtitle = chapter.titleHindi,
                                                content = chapter.summary,
                                                subject = chapter.subject.displayName,
                                                type = "Note"
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Text(
                            text = chapter.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = chapter.titleHindi,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = chapter.summary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )

                        // Expanded View with Key Points & Formulas
                        if (expandedChapter?.id == chapter.id) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                            Text(
                                text = "📌 NCERT High-Yield Key Points:",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )

                            chapter.keyPoints.forEach { point ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Text(text = "•", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = point,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                }
                            }

                            if (chapter.formulas.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "📐 Essential Formulas:",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )

                                chapter.formulas.forEach { formula ->
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(
                                                text = formula.name,
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            )
                                            Text(
                                                text = formula.formula,
                                                style = MaterialTheme.typography.titleSmall.copy(
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            )
                                            Text(
                                                text = formula.description,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    expandedChapter = if (expandedChapter?.id == chapter.id) null else chapter
                                }
                            ) {
                                Text(
                                    text = if (expandedChapter?.id == chapter.id) "Show Less 🔼" else "Read Full Notes 🔽",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
