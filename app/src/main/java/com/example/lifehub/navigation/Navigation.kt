package com.example.lifehub.navigation

import androidx.navigation.NavHostController
import com.example.lifehub.navigation.Screens.HOME_SCREEN

/**
 * Screens used in [Destinations]
 */
private object Screens {
    const val HOME_SCREEN = "home"
}

/**
 * Destinations used in the MainActivity
 */
object Destinations {
    const val HOME_ROUTE = HOME_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

}