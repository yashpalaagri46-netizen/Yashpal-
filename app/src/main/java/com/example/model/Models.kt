package com.example.model

import kotlinx.serialization.Serializable

enum class Subject(val displayName: String, val icon: String, val colorHex: Long) {
    PHYSICS("Physics", "⚡", 0xFF287CFF),
    CHEMISTRY("Chemistry", "🧪", 0xFFF59E0B),
    BIOLOGY("Biology", "🧬", 0xFF10B981)
}

data class Chapter(
    val id: String,
    val title: String,
    val titleHindi: String,
    val subject: Subject,
    val totalQuestions: Int,
    val summary: String,
    val keyPoints: List<String>,
    val formulas: List<FormulaItem> = emptyList(),
    val isHighYield: Boolean = false,
    val isCompleted: Boolean = false
)

data class FormulaItem(
    val name: String,
    val formula: String,
    val description: String
)

data class MCQQuestion(
    val id: String,
    val questionHindi: String,
    val questionEnglish: String,
    val subject: Subject,
    val chapter: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val yearTag: String? = null // e.g., "NEET 2023", "NEET 2024"
)

data class DPPItem(
    val id: String,
    val dayNumber: Int,
    val title: String,
    val subject: Subject,
    val questions: List<MCQQuestion>,
    val isCompleted: Boolean = false,
    val score: Int = 0
)

data class TestExam(
    val id: String,
    val title: String,
    val type: String, // "Full Mock", "Chapter Test", "Speed Test", "OMR Test"
    val durationMinutes: Int,
    val totalQuestions: Int,
    val questions: List<MCQQuestion>,
    val instructions: String
)

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean,
    val currentProgress: Int,
    val maxProgress: Int
)

data class StudyWebsiteItem(
    val name: String,
    val url: String,
    val description: String,
    val category: String,
    val icon: String
)

data class VideoLectureItem(
    val id: String,
    val title: String,
    val channel: String,
    val subject: Subject,
    val topic: String,
    val duration: String,
    val youtubeQuery: String
)

data class AppThemeData(
    val name: String,
    val emoji: String,
    val accentColor: Long,
    val accentSecondaryColor: Long,
    val bgColor: Long,
    val bgSecondaryColor: Long
)

data class AIChatMessage(
    val id: String,
    val sender: String, // "user" or "ai"
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
