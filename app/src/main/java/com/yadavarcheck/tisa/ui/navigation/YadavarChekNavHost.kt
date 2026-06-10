package com.yadavarcheck.tisa.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import com.yadavarcheck.tisa.ui.screens.splash.SplashScreen
import com.yadavarcheck.tisa.ui.screens.onboarding.OnboardingScreen
import com.yadavarcheck.tisa.ui.screens.dashboard.DashboardScreen
import com.yadavarcheck.tisa.ui.screens.checks.CheckListScreen
import com.yadavarcheck.tisa.ui.screens.checks.CheckDetailScreen
import com.yadavarcheck.tisa.ui.screens.checks.AddEditCheckScreen
import com.yadavarcheck.tisa.ui.screens.calendar.CalendarScreen
import com.yadavarcheck.tisa.ui.screens.reports.ReportsScreen
import com.yadavarcheck.tisa.ui.screens.customers.CustomersScreen
import com.yadavarcheck.tisa.ui.screens.notifications.NotificationsScreen
import com.yadavarcheck.tisa.ui.screens.settings.SettingsScreen
import com.yadavarcheck.tisa.ui.screens.backup.BackupScreen
import com.yadavarcheck.tisa.ui.screens.security.SecurityScreen

@Composable
fun YadavarChekNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToDashboard  = { navController.navigate(Screen.Dashboard.route) { popUpTo(Screen.Splash.route) { inclusive = true } } },
                onNavigateToOnboarding = { navController.navigate(Screen.Onboarding.route) { popUpTo(Screen.Splash.route) { inclusive = true } } }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = { navController.navigate(Screen.Dashboard.route) { popUpTo(Screen.Onboarding.route) { inclusive = true } } }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToChecks        = { navController.navigate(Screen.CheckList.route) },
                onNavigateToCalendar      = { navController.navigate(Screen.Calendar.route) },
                onNavigateToReports       = { navController.navigate(Screen.Reports.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings      = { navController.navigate(Screen.Settings.route) },
                onNavigateToAddCheck      = { navController.navigate("add_edit_check?checkId=-1") },
                onNavigateToCheckDetail   = { id -> navController.navigate("check_detail/$id") }
            )
        }
        composable(Screen.CheckList.route) {
            CheckListScreen(
                onBack              = { navController.popBackStack() },
                onNavigateToDetail  = { id -> navController.navigate("check_detail/$id") },
                onNavigateToAddEdit = { id -> navController.navigate("add_edit_check?checkId=$id") }
            )
        }
        composable(
            route = "check_detail/{checkId}",
            arguments = listOf(navArgument("checkId") { type = NavType.LongType })
        ) { backStack ->
            CheckDetailScreen(
                checkId = backStack.arguments?.getLong("checkId") ?: -1L,
                onBack  = { navController.popBackStack() },
                onEdit  = { id -> navController.navigate("add_edit_check?checkId=$id") }
            )
        }
        composable(
            route = "add_edit_check?checkId={checkId}",
            arguments = listOf(navArgument("checkId") { type = NavType.LongType; defaultValue = -1L })
        ) { backStack ->
            AddEditCheckScreen(
                checkId = backStack.arguments?.getLong("checkId") ?: -1L,
                onBack  = { navController.popBackStack() }
            )
        }
        composable(Screen.Calendar.route)       { CalendarScreen(onBack = { navController.popBackStack() }, onNavigateToDetail = { id -> navController.navigate("check_detail/$id") }) }
        composable(Screen.Reports.route)        { ReportsScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Customers.route)      { CustomersScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Notifications.route)  { NotificationsScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Settings.route)       { SettingsScreen(onBack = { navController.popBackStack() }, onNavigateToBackup = { navController.navigate(Screen.Backup.route) }, onNavigateToSecurity = { navController.navigate(Screen.Security.route) }) }
        composable(Screen.Backup.route)         { BackupScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Security.route)       { SecurityScreen(onBack = { navController.popBackStack() }) }
    }
}
