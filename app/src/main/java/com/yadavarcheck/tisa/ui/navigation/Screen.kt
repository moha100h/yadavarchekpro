package com.yadavarcheck.tisa.ui.navigation

sealed class Screen(val route: String) {
    object Splash        : Screen("splash")
    object Onboarding    : Screen("onboarding")
    object Dashboard     : Screen("dashboard")
    object CheckList     : Screen("check_list")
    object Calendar      : Screen("calendar")
    object Reports       : Screen("reports")
    object Customers     : Screen("customers")
    object Notifications : Screen("notifications")
    object Settings      : Screen("settings")
    object Backup        : Screen("backup")
    object Security      : Screen("security")

    object CheckDetail : Screen("check_detail/{checkId}") {
        fun createRoute(checkId: Long) = "check_detail/$checkId"
    }

    object AddEditCheck : Screen("add_edit_check/{checkId}") {
        fun createRoute(checkId: Long) = "add_edit_check/$checkId"
    }
}
