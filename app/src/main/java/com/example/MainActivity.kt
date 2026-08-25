package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.ParticleBackground
import com.example.ui.components.TopBarHeader
import com.example.ui.navigation.Screen
import com.example.ui.navigation.drawerNavItems
import com.example.ui.navigation.primaryNavItems
import com.example.ui.screens.*
import com.example.ui.theme.MissionLakshyaTheme
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val selectedTheme by mainViewModel.selectedTheme.collectAsState()
            val displayMode by mainViewModel.displayMode.collectAsState()

            MissionLakshyaTheme(
                activeTheme = selectedTheme,
                displayMode = displayMode
            ) {
                MainAppScaffold(viewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun MainAppScaffold(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val currentScreen = drawerNavItems.firstOrNull { it.route == currentRoute } ?: Screen.Home
    val isExamRunning = currentRoute == Screen.TestRun.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !isExamRunning,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    // Drawer Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🎯", fontSize = 26.sp)
                        }
                        Column {
                            Text(
                                text = "Mission Lakshya",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "NEET 2027 Digital Portal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Navigation Items
                    androidx.compose.foundation.lazy.LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(drawerNavItems.size) { index ->
                            val screen = drawerNavItems[index]
                            val isSelected = currentRoute == screen.route

                            NavigationDrawerItem(
                                icon = { Text(text = screen.icon, fontSize = 18.sp) },
                                label = {
                                    Text(
                                        text = screen.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    )
                                },
                                selected = isSelected,
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    selectedIconColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (!isExamRunning) {
                    TopBarHeader(
                        title = currentScreen.title,
                        subtitle = "NEET 2027",
                        onOpenDrawer = {
                            coroutineScope.launch { drawerState.open() }
                        },
                        actions = {
                            IconButton(
                                onClick = { navController.navigate(Screen.Themes.route) },
                                modifier = Modifier.testTag("topbar_theme_btn")
                            ) {
                                Text(text = "🎨", fontSize = 20.sp)
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (!isExamRunning) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp
                    ) {
                        primaryNavItems.forEach { screen ->
                            val isSelected = currentRoute == screen.route
                            NavigationBarItem(
                                icon = { Text(text = screen.icon, fontSize = 20.sp) },
                                label = {
                                    Text(
                                        text = screen.title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        maxLines = 1
                                    )
                                },
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Subtle Particle Animation Layer
                ParticleBackground(particleCount = 20)

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            viewModel = viewModel,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable(Screen.Dashboard.route) {
                        DashboardScreen(
                            viewModel = viewModel,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable(Screen.Books.route) {
                        BooksAndNotesScreen(viewModel = viewModel)
                    }
                    composable(Screen.AI.route) {
                        AIDoubtSolverScreen(viewModel = viewModel)
                    }
                    composable(Screen.QuestionBank.route) {
                        QuestionBankScreen(viewModel = viewModel)
                    }
                    composable(Screen.Tests.route) {
                        TestCenterScreen(
                            viewModel = viewModel,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable(Screen.TestRun.route) {
                        ActiveTestRunScreen(
                            viewModel = viewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.DPP.route) {
                        DPPScreen(
                            viewModel = viewModel,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable(Screen.Planner.route) {
                        StudyPlannerScreen(viewModel = viewModel)
                    }
                    composable(Screen.Revision.route) {
                        RevisionScreen(viewModel = viewModel)
                    }
                    composable(Screen.Performance.route) {
                        PerformanceScreen(viewModel = viewModel)
                    }
                    composable(Screen.Achievements.route) {
                        AchievementsScreen(viewModel = viewModel)
                    }
                    composable(Screen.Videos.route) {
                        VideosScreen(viewModel = viewModel)
                    }
                    composable(Screen.Websites.route) {
                        WebsitesScreen(viewModel = viewModel)
                    }
                    composable(Screen.Community.route) {
                        CommunityScreen(viewModel = viewModel)
                    }
                    composable(Screen.Themes.route) {
                        ThemesScreen(viewModel = viewModel)
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(viewModel = viewModel)
                    }
                    composable(Screen.Support.route) {
                        SupportScreen()
                    }
                }
            }
        }
    }
}
