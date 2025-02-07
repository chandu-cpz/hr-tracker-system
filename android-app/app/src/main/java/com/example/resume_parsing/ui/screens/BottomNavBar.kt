//package com.example.resume_parsing.ui.components
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.BusinessCenter
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//
//@Composable
//fun BottomNavBar(navController: NavController) {
//    val navItems = listOf(
//        BottomNavItem("recommended", "Recommended", Icons.Default.Home),
//        BottomNavItem("joboffers", "Job Offers", Icons.Default.BusinessCenter),
//        BottomNavItem("profile", "Profile", Icons.Default.Person)
//    )
//
//    NavigationBar(
//        containerColor = Color.Black, // Background color of the nav bar
//        contentColor = Color.Cyan // Active icon/text color
//    ) {
//        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//
//        navItems.forEach { item ->
//            NavigationBarItem(
//                selected = currentRoute == item.route,
//                onClick = { navController.navigate(item.route) },
//                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
//                label = { Text(item.label, color = Color.White) }
//            )
//        }
//    }
//}
//
//data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
package com.example.resume_parsing.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("recommended_jobs", "Recommended", Icons.Default.Home),
        BottomNavItem("job_offers", "Offers", Icons.Default.Business),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
