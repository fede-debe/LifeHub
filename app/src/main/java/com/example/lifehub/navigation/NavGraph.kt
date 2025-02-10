package com.example.lifehub.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lifehub.navigation.DestinationsArgs.SNACK_BAR_MESSAGE_ARG
import com.example.lifehub.ui.screens.home.HomeScreen
import com.example.lifehub.ui.screens.notes.NotesScreen
import com.example.lifehub.ui.screens.notes.add.AddNoteScreen
import com.example.lifehub.ui.screens.notes.notedetails.NoteDetailsScreen
import kotlinx.coroutines.CoroutineScope

const val DELETE_RESULT_OK = Activity.RESULT_CANCELED + 1

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = Destinations.HOME_ROUTE,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            Destinations.HOME_ROUTE
        ) {
            HomeScreen(onClickCategory = { navActions.navigateToNotesScreen() })
        }

        composable(Destinations.NOTES_ROUTE, arguments = listOf(
            navArgument(SNACK_BAR_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
        )) { entry ->
            NotesScreen(
                snackBarMessage = entry.arguments?.getInt(SNACK_BAR_MESSAGE_ARG)!!,
                onSnackBarMessageDisplayed = { entry.arguments?.putInt(SNACK_BAR_MESSAGE_ARG, 0) },
                onClickAddNote = { navActions.navigateToAddNoteScreen() },
                onNoteClick = { noteId ->
                    navActions.navigateToNoteDetailScreen(noteId = noteId)
                },
                onClickBack = { navController.navigateUp() })
        }

        composable(Destinations.ADD_NOTE_ROUTE) {
            AddNoteScreen(onClickBack = { navController.navigateUp() })
        }

        composable(Destinations.NOTE_DETAILS_ROUTE) {
            NoteDetailsScreen(onClickBack = { navController.navigateUp() },
                onDeleteNote = {
                    navActions.navigateToNotesScreen(DELETE_RESULT_OK)
                })
        }
    }
}