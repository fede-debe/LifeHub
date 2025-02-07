package com.example.lifehub.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.lifehub.navigation.Screens.ADD_NOTE_SCREEN
import com.example.lifehub.navigation.Screens.HOME_SCREEN
import com.example.lifehub.navigation.Screens.NOTES_SCREEN

/**
 * Screens used in [Destinations]
 */
private object Screens {
    const val HOME_SCREEN = "home"
    const val NOTES_SCREEN = "notes"
    const val ADD_NOTE_SCREEN = "add_note"
}

/**
 * Destinations used in the MainActivity
 */
object Destinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val NOTES_ROUTE = NOTES_SCREEN
    const val ADD_NOTE_ROUTE = ADD_NOTE_SCREEN
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToNotesScreen() {
        navController.navigate(Destinations.NOTES_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToAddNoteScreen() {
        navController.navigate(Destinations.ADD_NOTE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}