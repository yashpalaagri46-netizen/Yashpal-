package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        StudyPlanEntity::class,
        TestResultEntity::class,
        BookmarkEntity::class,
        CommunityPostEntity::class,
        UserProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studyPlanDao(): StudyPlanDao
    abstract fun testResultDao(): TestResultDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun communityPostDao(): CommunityPostDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mission_lakshya_neet.db"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Pre-populate default profile and seed items
                        CoroutineScope(Dispatchers.IO).launch {
                            INSTANCE?.let { database ->
                                database.userProfileDao().saveUserProfile(
                                    UserProfileEntity(
                                        id = 1,
                                        name = "Yashpal Aagri",
                                        targetYear = "2027",
                                        dreamCollege = "AIIMS New Delhi",
                                        targetScore = 700,
                                        dailyStudyHours = 8,
                                        streakDays = 5,
                                        selectedTheme = "Ocean Blue",
                                        displayMode = "dark"
                                    )
                                )
                                database.studyPlanDao().insertPlan(
                                    StudyPlanEntity(
                                        title = "Revise Cell Cycle & Cell Division (NCERT Line-by-Line)",
                                        subject = "Biology",
                                        targetDate = "Today",
                                        isCompleted = false
                                    )
                                )
                                database.studyPlanDao().insertPlan(
                                    StudyPlanEntity(
                                        title = "Solve 30 MCQs on Thermodynamics & Work Energy",
                                        subject = "Physics",
                                        targetDate = "Today",
                                        isCompleted = false
                                    )
                                )
                                database.studyPlanDao().insertPlan(
                                    StudyPlanEntity(
                                        title = "Memorize Periodic Trends & Chemical Bonding Formulas",
                                        subject = "Chemistry",
                                        targetDate = "Tomorrow",
                                        isCompleted = true
                                    )
                                )
                                database.communityPostDao().insertPost(
                                    CommunityPostEntity(
                                        author = "Aman Sharma",
                                        title = "Important NCERT Biology diagrams for NEET 2027?",
                                        content = "Which diagrams in Plant Physiology have highest weightage in PYQs?",
                                        subject = "Biology",
                                        upvotes = 14
                                    )
                                )
                                database.communityPostDao().insertPost(
                                    CommunityPostEntity(
                                        author = "Priya Patel",
                                        title = "Trick to remember Electronegativity order in Chemistry",
                                        content = "Use FONClBrISCH! Fluorine > Oxygen > Nitrogen > Chlorine > Bromine...",
                                        subject = "Chemistry",
                                        upvotes = 28
                                    )
                                )
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
