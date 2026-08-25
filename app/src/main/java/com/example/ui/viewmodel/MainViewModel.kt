package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkEntity
import com.example.data.local.CommunityPostEntity
import com.example.data.local.StudyPlanEntity
import com.example.data.local.TestResultEntity
import com.example.data.local.UserProfileEntity
import com.example.data.remote.GeminiClient
import com.example.data.repository.NeetRepository
import com.example.model.AIChatMessage
import com.example.model.AppThemeData
import com.example.model.Chapter
import com.example.model.DPPItem
import com.example.model.MCQQuestion
import com.example.model.Subject
import com.example.model.TestExam
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NeetRepository(AppDatabase.getDatabase(application))

    // Themes & Modes
    val allThemes = repository.themesList
    private val _selectedTheme = MutableStateFlow(allThemes[0])
    val selectedTheme: StateFlow<AppThemeData> = _selectedTheme.asStateFlow()

    private val _displayMode = MutableStateFlow("dark")
    val displayMode: StateFlow<String> = _displayMode.asStateFlow()

    // Database state flows
    val studyPlans: StateFlow<List<StudyPlanEntity>> = repository.studyPlans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val testResults: StateFlow<List<TestResultEntity>> = repository.testResults
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.bookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val communityPosts: StateFlow<List<CommunityPostEntity>> = repository.communityPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Static / Curated Content
    val chapters = repository.chapters
    val allQuestions = repository.questionsList
    val mockTests = repository.mockTests
    val dailyDPPs = repository.dailyDPPs
    val studyWebsites = repository.studyWebsites
    val videoLectures = repository.videoLectures
    val achievements = repository.achievements

    // AI Chat State
    private val _aiMessages = MutableStateFlow<List<AIChatMessage>>(
        listOf(
            AIChatMessage(
                id = "ai_welcome",
                sender = "ai",
                message = "नमस्ते! मैं Mission Lakshya AI Tutor हूँ। NEET 2027 के Physics, Chemistry और Biology के किसी भी doubt, formula derivation या question के समाधान के लिए पूछें!"
            )
        )
    )
    val aiMessages: StateFlow<List<AIChatMessage>> = _aiMessages.asStateFlow()

    private val _aiLoading = MutableStateFlow(false)
    val aiLoading: StateFlow<Boolean> = _aiLoading.asStateFlow()

    private val _aiError = MutableStateFlow<String?>(null)
    val aiError: StateFlow<String?> = _aiError.asStateFlow()

    // Test & Quiz Session State
    private val _activeTest = MutableStateFlow<TestExam?>(null)
    val activeTest: StateFlow<TestExam?> = _activeTest.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _userSelectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val userSelectedAnswers: StateFlow<Map<Int, Int>> = _userSelectedAnswers.asStateFlow()

    private val _testRemainingSeconds = MutableStateFlow(0)
    val testRemainingSeconds: StateFlow<Int> = _testRemainingSeconds.asStateFlow()

    private val _testSubmittedResult = MutableStateFlow<TestResultEntity?>(null)
    val testSubmittedResult: StateFlow<TestResultEntity?> = _testSubmittedResult.asStateFlow()

    private var timerJob: Job? = null

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setTheme(theme: AppThemeData) {
        _selectedTheme.value = theme
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfileEntity()
            repository.updateProfile(current.copy(selectedTheme = theme.name))
        }
    }

    fun setDisplayMode(mode: String) {
        _displayMode.value = mode
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfileEntity()
            repository.updateProfile(current.copy(displayMode = mode))
        }
    }

    // Study Planner actions
    fun addStudyPlan(title: String, subject: String, targetDate: String = "Today") {
        viewModelScope.launch {
            if (title.isNotBlank()) {
                repository.addPlan(title.trim(), subject, targetDate)
            }
        }
    }

    fun togglePlan(plan: StudyPlanEntity) {
        viewModelScope.launch {
            repository.togglePlanCompletion(plan)
        }
    }

    fun deletePlan(plan: StudyPlanEntity) {
        viewModelScope.launch {
            repository.deletePlan(plan)
        }
    }

    // Bookmark actions
    fun addBookmark(title: String, subtitle: String, content: String, subject: String, type: String = "Note") {
        viewModelScope.launch {
            repository.addBookmark(title, subtitle, content, subject, type)
        }
    }

    fun deleteBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    // Community actions
    fun postCommunityQuestion(author: String, title: String, content: String, subject: String) {
        viewModelScope.launch {
            if (title.isNotBlank()) {
                repository.addCommunityPost(
                    author = author.ifBlank { "NEET Aspirant" },
                    title = title.trim(),
                    content = content.trim(),
                    subject = subject
                )
            }
        }
    }

    fun upvotePost(postId: Long) {
        viewModelScope.launch {
            repository.upvotePost(postId)
        }
    }

    // Profile actions
    fun updateProfile(name: String, dreamCollege: String, targetScore: Int, studyHours: Int) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfileEntity()
            repository.updateProfile(
                current.copy(
                    name = name,
                    dreamCollege = dreamCollege,
                    targetScore = targetScore,
                    dailyStudyHours = studyHours
                )
            )
        }
    }

    // AI Ask Doubt
    fun sendDoubtToAI(questionText: String) {
        if (questionText.isBlank()) return
        val userMsg = AIChatMessage(
            id = "user_${System.currentTimeMillis()}",
            sender = "user",
            message = questionText.trim()
        )
        _aiMessages.value = _aiMessages.value + userMsg
        _aiLoading.value = true
        _aiError.value = null

        viewModelScope.launch {
            val result = GeminiClient.askNeetDoubt(questionText.trim())
            _aiLoading.value = false
            result.onSuccess { responseText ->
                val aiMsg = AIChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    sender = "ai",
                    message = responseText
                )
                _aiMessages.value = _aiMessages.value + aiMsg
            }.onFailure { err ->
                _aiError.value = err.localizedMessage ?: "AI connection failed."
                val errorMsg = AIChatMessage(
                    id = "ai_err_${System.currentTimeMillis()}",
                    sender = "ai",
                    message = "⚠️ ${err.localizedMessage ?: "AI सेवा से संपर्क नहीं हो सका। कृपया अपनी इंटरनेट कनेक्टिविटी और API कुंजी जाँचें।"}"
                )
                _aiMessages.value = _aiMessages.value + errorMsg
            }
        }
    }

    // Test & Quiz Logic
    fun startTestExam(exam: TestExam) {
        _activeTest.value = exam
        _currentQuestionIndex.value = 0
        _userSelectedAnswers.value = emptyMap()
        _testRemainingSeconds.value = exam.durationMinutes * 60
        _testSubmittedResult.value = null

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_testRemainingSeconds.value > 0) {
                delay(1000)
                _testRemainingSeconds.value -= 1
            }
            if (_testSubmittedResult.value == null) {
                submitTest()
            }
        }
    }

    fun selectOption(questionIndex: Int, optionIndex: Int) {
        _userSelectedAnswers.value = _userSelectedAnswers.value + (questionIndex to optionIndex)
    }

    fun nextQuestion() {
        val exam = _activeTest.value ?: return
        if (_currentQuestionIndex.value < exam.questions.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun jumpToQuestion(index: Int) {
        val exam = _activeTest.value ?: return
        if (index in 0 until exam.questions.size) {
            _currentQuestionIndex.value = index
        }
    }

    fun submitTest() {
        timerJob?.cancel()
        val exam = _activeTest.value ?: return
        val answers = _userSelectedAnswers.value

        var correctCount = 0
        var wrongCount = 0

        exam.questions.forEachIndexed { index, question ->
            val userChoice = answers[index]
            if (userChoice != null) {
                if (userChoice == question.correctIndex) {
                    correctCount++
                } else {
                    wrongCount++
                }
            }
        }

        val totalAttempted = correctCount + wrongCount
        val score = (correctCount * 4) - (wrongCount * 1)
        val maxScore = exam.questions.size * 4
        val accuracy = if (totalAttempted > 0) (correctCount.toFloat() / totalAttempted) * 100f else 0f
        val timeSpent = (exam.durationMinutes * 60) - _testRemainingSeconds.value

        val primarySubject = exam.questions.firstOrNull()?.subject?.displayName ?: "PCB Full Mock"

        val resultEntity = TestResultEntity(
            testTitle = exam.title,
            subject = primarySubject,
            score = score,
            maxScore = maxScore,
            totalQuestions = exam.questions.size,
            correctCount = correctCount,
            wrongCount = wrongCount,
            accuracy = accuracy,
            timeTakenSeconds = timeSpent
        )

        _testSubmittedResult.value = resultEntity

        viewModelScope.launch {
            repository.saveTestResult(resultEntity)
        }
    }

    fun exitTest() {
        timerJob?.cancel()
        _activeTest.value = null
        _testSubmittedResult.value = null
    }
}
