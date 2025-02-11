package com.example.lifehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lifehub.R
import com.example.lifehub.ui.screens.home.HomeScreen
import com.example.lifehub.ui.screens.notes.NotesResultViewModel
import com.example.lifehub.ui.screens.notes.NotesScreen
import com.example.lifehub.ui.screens.notes.add.AddNoteScreen
import com.example.lifehub.ui.screens.notes.notedetails.NoteDetailsScreen
import kotlinx.coroutines.CoroutineScope


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = Destinations.HOME_ROUTE,
    resultViewModel: NotesResultViewModel = hiltViewModel(),
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

        composable(Destinations.NOTES_ROUTE) {
            NotesScreen(
                resultViewModel = resultViewModel,
                onClickAddNote = { navActions.navigateToAddNoteScreen() },
                onNoteClick = { noteId ->
                    navActions.navigateToNoteDetailScreen(noteId = noteId)
                },
                onClickBack = { navController.navigateUp() })
        }

        composable(Destinations.ADD_NOTE_ROUTE) {
            AddNoteScreen(onClickBack = { navController.popBackStack() })
        }

        composable(Destinations.NOTE_DETAILS_ROUTE) {
            NoteDetailsScreen(resultViewModel = resultViewModel,
                onClickBack = { navController.navigateUp() },
                onDeleteNote = {
                    resultViewModel.postSnackBarMessage(R.string.successfully_deleted_note_message)
                    navController.popBackStack()
                })
        }
    }
}