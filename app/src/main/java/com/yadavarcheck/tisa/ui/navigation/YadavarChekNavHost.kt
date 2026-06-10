package com.yadavarcheck.tisa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yadavarcheck.tisa.ui.screens.backup.BackupScreen
import com.yadavarcheck.tisa.ui.screens.calendar.CalendarScreen
import com.yadavarcheck.tisa.ui.screens.checks.AddEditCheckScreen
import com.yadavarcheck.tisa.ui.screens.checks.CheckDetailScreen
import com.yadavarcheck.tisa.ui.screens.checks.CheckListScreen
import com.yadavarcheck.tisa.ui.screens.customers.CustomersScreen
import com.yadavarcheck.tisa.ui.screens.dashboard.DashboardScreen
import com.yadavarcheck.tisa.ui.screens.notifications.NotificationsScreen
import com.yadavarcheck.tisa.ui.screens.onboarding.OnboardingScreen
import com.yadavarcheck.tisa.ui.screens.reports.ReportsScreen
import com.yadavarcheck.tisa.ui.screens.security.SecurityScreen
import com.yadavarcheck.tisa.ui.screens.settings.SettingsScreen
import com.yadavarcheck.tisa.ui.screens.splash.SplashScreen

@Composable
fun YadavarChekNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
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
                onNavigateToAddCheck      = { navController.navigate(Screen.AddEditCheck.createRoute(-1L)) },
                onNavigateToCheckDetail   = { id -> navController.navigate(Screen.CheckDetail.createRoute(id)) }
            )
        }

        composable(Screen.CheckList.route) {
            CheckListScreen(
                onBack              = { navController.popBackStack() },
                onNavigateToDetail  = { id -> navController.navigate(Screen.CheckDetail.createRoute(id)) },
                onNavigateToAddEdit = { id -> navController.navigate(Screen.AddEditCheck.createRoute(id)) }
            )
        }

        composable(
            route = Screen.CheckDetail.route,
            arguments = listOf(navArgument("checkId") { type = NavType.LongType })
        ) { backStack ->
            val checkId = backStack.arguments?.getLong("checkId") ?: -1L
            CheckDetailScreen(
                checkId = checkId,
                onBack  = { navController.popBackStack() },
                onEdit  = { id -> navController.navigate(Screen.AddEditCheck.createRoute(id)) }
            )
        }

        composable(
            route = Screen.AddEditCheck.route,
            arguments = listOf(navArgument("checkId") { type = NavType.LongType; defaultValue = -1L })
        ) { backStack ->
            val checkId = backStack.arguments?.getLong("checkId") ?: -1L
            AddEditCheckScreen(
                checkId = checkId,
                onBack  = { navController.popBackStack() }
            )
        }

        composable(Screen.Calendar.route) {
            CalendarScreen(
                onBack                = { navController.popBackStack() },
                onNavigateToDetail    = { id -> navController.navigate(Screen.CheckDetail.createRoute(id)) }
            )
        }

        composable(Screen.Reports.route) {
            ReportsScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Customers.route) {
            CustomersScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack               = { navController.popBackStack() },
                onNavigateToBackup   = { navController.navigate(Screen.Backup.route) },
                onNavigateToSecurity = { navController.navigate(Screen.Security.route) }
            )
        }

        composable(Screen.Backup.route) {
            BackupScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Security.route) {
            SecurityScreen(onBack = { navController.popBackStack() })
        }
    }
}
