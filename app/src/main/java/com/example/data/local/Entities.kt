package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_plans")
data class StudyPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val subject: String,
    val targetDate: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "test_results")
data class TestResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val testTitle: String,
    val subject: String,
    val score: Int,
    val maxScore: Int,
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val accuracy: Float,
    val timeTakenSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val subtitle: String,
    val content: String,
    val subject: String,
    val type: String, // "Question", "Note", "Formula"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "community_posts")
data class CommunityPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val author: String,
    val title: String,
    val content: String,
    val subject: String,
    val upvotes: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Yashpal Aagri",
    val targetYear: String = "2027",
    val dreamCollege: String = "AIIMS New Delhi",
    val targetScore: Int = 700,
    val dailyStudyHours: Int = 8,
    val streakDays: Int = 5,
    val selectedTheme: String = "Ocean Blue",
    val displayMode: String = "dark"
)
