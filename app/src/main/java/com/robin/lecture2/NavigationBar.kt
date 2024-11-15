package com.robin.lecture2

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// IMPORT
// implementation(libs.androidx.navigation.compose)
// implementation(libs.androidx.navigation.runtime.ktx)
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

@Composable
fun BottomNavigationBarExample() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        // Host the composable navigation content
        NavigationHost(navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(NavItem.Notifications, NavItem.Settings, NavItem.Tools)
    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title, color = Color.Black) }
            )
        }
    }
}

// A helper function to get the current route
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = NavItem.Notifications.route,
        modifier = modifier,
//        enterTransition = { EnterTransition.None },
//        exitTransition = { ExitTransition.None }
    ) {
        composable(NavItem.Notifications.route) { TabScreen("Notifications") }
        composable(NavItem.Tools.route) { TabScreen("Tools") }
        composable(NavItem.Settings.route) { TabScreen("Settings") }
    }
}

@Composable
fun TabScreen(tabName: String) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(tabName)
    }
}

// Navigation item data class
sealed class NavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val title: String) {
    object Notifications : NavItem("tab1", Icons.Filled.Notifications, "Notifications")
    object Tools : NavItem("tab2", Icons.Filled.Build, "Tools")
    object Settings : NavItem("tab3", Icons.Filled.Settings, "Settings")
}