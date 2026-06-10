package com.yadavarcheck.tisa.ui.navigation

sealed class Screen(val route: String) {
    object Splash      : Screen("splash")
    object Onboarding  : Screen("onboarding")
    object Dashboard   : Screen("dashboard")
    object CheckList   : Screen("check_list")
    object CheckDetail : Screen("check_detail/{checkId}")
    object AddEditCheck: Screen("add_edit_check?checkId={checkId}")
    object Calendar    : Screen("calendar")
    object Reports     : Screen("reports")
    object Customers   : Screen("customers")
    object CustomerDetail: Screen("customer_detail/{customerId}")
    object Notifications: Screen("notifications")
    object Settings    : Screen("settings")
    object Backup      : Screen("backup")
    object Security    : Screen("security")

    fun withArgs(vararg args: Any) = buildString {
        var r = route
        args.forEach { arg -> r = r.replaceFirst(Regex("\\{[^}]+}"), arg.toString()) }
        r
    }
}
