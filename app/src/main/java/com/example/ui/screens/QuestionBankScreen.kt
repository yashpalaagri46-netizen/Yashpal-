package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
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
import com.example.model.MCQQuestion
import com.example.model.Subject
import com.example.ui.components.SubjectBadge
import com.example.ui.viewmodel.MainViewModel

@Composable
fun QuestionBankScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val questions = viewModel.allQuestions
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    val userAnswers = remember { mutableStateMapOf<String, Int>() }
    val bookmarks by viewModel.bookmarks.collectAsState()

    val filteredQuestions = remember(selectedSubject) {
        if (selectedSubject == null) questions else questions.filter { it.subject == selectedSubject }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Subject Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedSubject == null,
                onClick = { selectedSubject = null },
                label = { Text("All PCB") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            itemsIndexed(filteredQuestions, key = { _, q -> q.id }) { index, question ->
                val selectedOption = userAnswers[question.id]
                val isAnswered = selectedOption != null
                val isBookmarked = bookmarks.any { it.title == question.id }

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("question_card_${question.id}")
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Q${index + 1}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                SubjectBadge(subject = question.subject)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                if (question.yearTag != null) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = question.yearTag,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        if (isBookmarked) {
                                            val b = bookmarks.firstOrNull { it.title == question.id }
                                            if (b != null) viewModel.deleteBookmark(b)
                                        } else {
                                            viewModel.addBookmark(
                                                title = question.id,
                                                subtitle = question.questionHindi,
                                                content = question.explanation,
                                                subject = question.subject.displayName,
                                                type = "Question"
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
                            text = question.questionHindi,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = question.questionEnglish,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // MCQ Options
                        question.options.forEachIndexed { optIndex, optionText ->
                            val isChosen = selectedOption == optIndex
                            val isCorrect = optIndex == question.correctIndex

                            val (bgColor, borderColor, textColor) = when {
                                !isAnswered -> Triple(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.onSurface
                                )
                                isCorrect -> Triple(
                                    Color(0xFF064E3B).copy(alpha = 0.3f),
                                    Color(0xFF10B981),
                                    Color(0xFF10B981)
                                )
                                isChosen && !isCorrect -> Triple(
                                    Color(0xFF7F1D1D).copy(alpha = 0.3f),
                                    Color(0xFFEF4444),
                                    Color(0xFFEF4444)
                                )
                                else -> Triple(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = bgColor,
                                border = if (isAnswered && (isCorrect || isChosen)) ButtonDefaults.outlinedButtonBorder else null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = !isAnswered) {
                                        userAnswers[question.id] = optIndex
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = textColor,
                                            fontWeight = if (isChosen || (isAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (isAnswered) {
                                        if (isCorrect) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Correct",
                                                tint = Color(0xFF10B981),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        } else if (isChosen) {
                                            Icon(
                                                imageVector = Icons.Default.Cancel,
                                                contentDescription = "Wrong",
                                                tint = Color(0xFFEF4444),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Explanation
                        if (isAnswered) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "💡 Explanation:",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        text = question.explanation,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurface
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
}
