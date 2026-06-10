package com.yadavarcheck.tisa.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.*; import androidx.navigation.compose.*
import com.yadavarcheck.tisa.ui.screens.backup.BackupScreen
import com.yadavarcheck.tisa.ui.screens.calendar.CalendarScreen
import com.yadavarcheck.tisa.ui.screens.checks.*
import com.yadavarcheck.tisa.ui.screens.customers.CustomersScreen
import com.yadavarcheck.tisa.ui.screens.dashboard.DashboardScreen
import com.yadavarcheck.tisa.ui.screens.notifications.NotificationsScreen
import com.yadavarcheck.tisa.ui.screens.reports.ReportsScreen
import com.yadavarcheck.tisa.ui.screens.settings.SettingsScreen

@Composable
fun YadavarChekNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToChecks        = { navController.navigate(Screen.CheckList.route) },
                onNavigateToCalendar      = { navController.navigate(Screen.Calendar.route) },
                onNavigateToReports       = { navController.navigate(Screen.Reports.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings      = { navController.navigate(Screen.Settings.route) },
                onNavigateToAddCheck      = { navController.navigate(Screen.AddEditCheck.createRoute(0L)) },
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
        composable(Screen.CheckDetail.route, arguments = listOf(navArgument("checkId") { type = NavType.LongType })) { back ->
            CheckDetailScreen(checkId = back.arguments?.getLong("checkId") ?: 0L, onBack = { navController.popBackStack() }, onEdit = { id -> navController.navigate(Screen.AddEditCheck.createRoute(id)) })
        }
        composable(Screen.AddEditCheck.route, arguments = listOf(navArgument("checkId") { type = NavType.LongType })) { back ->
            AddEditCheckScreen(checkId = back.arguments?.getLong("checkId") ?: 0L, onBack = { navController.popBackStack() })
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(onBack = { navController.popBackStack() }, onNavigateToDetail = { id -> navController.navigate(Screen.CheckDetail.createRoute(id)) })
        }
        composable(Screen.Reports.route) { ReportsScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Customers.route) { CustomersScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { navController.popBackStack() }, onNavigateToCheck = { id -> navController.navigate(Screen.CheckDetail.createRoute(id)) })
        }
        composable(Screen.Settings.route) { SettingsScreen(onBack = { navController.popBackStack() }, onNavigateToBackup = { navController.navigate(Screen.Backup.route) }) }
        composable(Screen.Backup.route) { BackupScreen(onBack = { navController.popBackStack() }) }
    }
}
