package com.example.lifehub.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.lifehub.navigation.Destinations.LISTS_ROUTE
import com.example.lifehub.navigation.Destinations.NOTES_ROUTE
import com.example.lifehub.navigation.Destinations.TODO_ROUTE
import com.example.lifehub.navigation.DestinationsArgs.NOTE_ID
import com.example.lifehub.navigation.Screens.ADD_NOTE_SCREEN
import com.example.lifehub.navigation.Screens.ADD_TODO_LIST
import com.example.lifehub.navigation.Screens.HOME_SCREEN
import com.example.lifehub.navigation.Screens.LISTS_SCREEN
import com.example.lifehub.navigation.Screens.NOTES_SCREEN
import com.example.lifehub.navigation.Screens.NOTE_DETAILS_SCREEN
import com.example.lifehub.navigation.Screens.TODO_LIST_SCREEN

/**
 * Screens used in [Destinations]
 */
private object Screens {
    const val HOME_SCREEN = "home"
    // Notes
    const val NOTES_SCREEN = "notes"
    const val ADD_NOTE_SCREEN = "add_note"
    const val NOTE_DETAILS_SCREEN = "note_details"
    // Lists
    const val LISTS_SCREEN = "lists"
    const val TODO_LIST_SCREEN = "todo_list"
    const val ADD_TODO_LIST = "add_todo_list"
}

/**
 * Destinations used in the MainActivity
 */
object Destinations {
    const val HOME_ROUTE = HOME_SCREEN
    // Notes
    const val NOTES_ROUTE = NOTES_SCREEN
    const val ADD_NOTE_ROUTE = ADD_NOTE_SCREEN
    const val NOTE_DETAILS_ROUTE = "$NOTE_DETAILS_SCREEN/{$NOTE_ID}"
    // Lists
    const val LISTS_ROUTE = LISTS_SCREEN
    const val TODO_ROUTE = TODO_LIST_SCREEN
    const val TODO_ADD_LIST_ROUTE = ADD_TODO_LIST
}

/**
 * Arguments used in [Destinations] routes
 */
object DestinationsArgs {
    const val NOTE_ID = "noteId"
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToNotesScreen() {
        navController.navigate(NOTES_ROUTE) {
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

    fun navigateToListsScreen() {
        navController.navigate(LISTS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToTodoListScreen() {
        navController.navigate(TODO_ROUTE) {
            launchSingleTop = true
        }
    }

    fun navigateToAddTodoListScreen() {
        navController.navigate(Destinations.TODO_ADD_LIST_ROUTE) {
            launchSingleTop = true
        }
    }
}