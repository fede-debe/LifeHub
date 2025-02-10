package com.example.lifehub.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.lifehub.navigation.DestinationsArgs.NOTE_ID
import com.example.lifehub.navigation.DestinationsArgs.SNACK_BAR_MESSAGE_ARG
import com.example.lifehub.navigation.Screens.ADD_NOTE_SCREEN
import com.example.lifehub.navigation.Screens.HOME_SCREEN
import com.example.lifehub.navigation.Screens.NOTES_SCREEN
import com.example.lifehub.navigation.Screens.NOTE_DETAILS_SCREEN

/**
 * Screens used in [Destinations]
 */
private object Screens {
    const val HOME_SCREEN = "home"
    const val NOTES_SCREEN = "notes"
    const val ADD_NOTE_SCREEN = "add_note"
    const val NOTE_DETAILS_SCREEN = "note_details"
}

/**
 * Destinations used in the MainActivity
 */
object Destinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val NOTES_ROUTE = "$NOTES_SCREEN?$SNACK_BAR_MESSAGE_ARG={$SNACK_BAR_MESSAGE_ARG}"
    const val ADD_NOTE_ROUTE = ADD_NOTE_SCREEN
    const val NOTE_DETAILS_ROUTE = "$NOTE_DETAILS_SCREEN/{$NOTE_ID}"
}

/**
 * Arguments used in [Destinations] routes
 */
object DestinationsArgs {
    const val NOTE_ID = "noteId"
    const val SNACK_BAR_MESSAGE_ARG = "snackBarMessage"
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToNotesScreen(snackBarMessage: Int = 0) {
        navController.navigate(NOTES_SCREEN.let { value ->
            if (snackBarMessage != 0) "$value?$SNACK_BAR_MESSAGE_ARG=$snackBarMessage" else value
        }) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToAddNoteScreen() {
        navController.navigate(Destinations.ADD_NOTE_ROUTE) {
            launchSingleTop = true
        }
    }

    fun navigateToNoteDetailScreen(noteId: String) {
        navController.navigate("$NOTE_DETAILS_SCREEN/$noteId")
    }
}