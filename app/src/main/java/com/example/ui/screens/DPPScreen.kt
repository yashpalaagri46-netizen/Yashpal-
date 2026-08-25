package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.model.DPPItem
import com.example.model.Subject
import com.example.model.TestExam
import com.example.ui.components.GradientCard
import com.example.ui.components.SubjectBadge
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.MainViewModel

@Composable
fun DPPScreen(
    viewModel: MainViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dppList = viewModel.dailyDPPs
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }

    val filteredDPPs = remember(selectedSubject) {
        if (selectedSubject == null) dppList else dppList.filter { it.subject == selectedSubject }
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
                    text = "📋 Daily Practice Problems (DPP)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Solve 30 daily curated questions per subject to maintain strong streak and problem solving speed.",
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
                    label = { Text("All DPPs") },
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

        items(filteredDPPs) { dpp ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dpp_item_${dpp.id}")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SubjectBadge(subject = dpp.subject)
                            if (dpp.isCompleted) {
                                Surface(
                                    color = Color(0xFF10B981).copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "✅ Solved (${dpp.score} pts)",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color(0xFF10B981),
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = dpp.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )

                        Text(
                            text = "Day ${dpp.dayNumber} • 30 Questions • High Yield",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Button(
                        onClick = {
                            val exam = TestExam(
                                id = dpp.id,
                                title = dpp.title,
                                type = "DPP Test",
                                durationMinutes = 30,
                                totalQuestions = dpp.questions.size,
                                questions = dpp.questions,
                                instructions = "Solve all questions within 30 minutes. Marking: +4, -1."
                            )
                            viewModel.startTestExam(exam)
                            onNavigate(Screen.TestRun.route)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("start_dpp_btn_${dpp.id}")
                    ) {
                        Text(if (dpp.isCompleted) "Re-take 🔄" else "Solve ✍️")
                    }
                }
            }
        }
    }
}
