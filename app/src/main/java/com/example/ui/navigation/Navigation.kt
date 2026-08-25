package com.example.ui.navigation

sealed class Screen(val route: String, val title: String, val icon: String) {
    data object Home : Screen("home", "Home", "🏠")
    data object Dashboard : Screen("dashboard", "Dashboard", "📊")
    data object Books : Screen("books", "Books & Notes", "📚")
    data object AI : Screen("ai", "AI Doubt Solver", "🤖")
    data object QuestionBank : Screen("questionbank", "Question Bank", "🧠")
    data object Tests : Screen("tests", "NEET Tests", "📝")
    data object TestRun : Screen("test_run", "Active Exam", "⏱️")
    data object DPP : Screen("dpp", "DPP", "📋")
    data object Planner : Screen("planner", "Study Planner", "📅")
    data object Revision : Screen("revision", "Revision", "🔄")
    data object Performance : Screen("performance", "Performance", "📈")
    data object Achievements : Screen("achievements", "Achievements", "🏆")
    data object Videos : Screen("videos", "Video Lectures", "▶️")
    data object Websites : Screen("websites", "Study Websites", "🌐")
    data object Community : Screen("community", "Community", "💬")
    data object Themes : Screen("themes", "35 Themes", "🎨")
    data object Profile : Screen("profile", "Profile", "👤")
    data object Support : Screen("support", "Support", "📲")
}

val primaryNavItems = listOf(
    Screen.Home,
    Screen.Books,
    Screen.AI,
    Screen.Tests,
    Screen.Planner
)

val drawerNavItems = listOf(
    Screen.Home,
    Screen.Dashboard,
    Screen.Profile,
    Screen.Books,
    Screen.Videos,
    Screen.AI,
    Screen.QuestionBank,
    Screen.Tests,
    Screen.DPP,
    Screen.Planner,
    Screen.Revision,
    Screen.Performance,
    Screen.Achievements,
    Screen.Community,
    Screen.Websites,
    Screen.Themes,
    Screen.Support
)
