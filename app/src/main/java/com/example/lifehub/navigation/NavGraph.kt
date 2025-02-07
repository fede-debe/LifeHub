package com.example.lifehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lifehub.ui.HomeScreen
import com.example.lifehub.ui.notes.NotesScreen
import com.example.lifehub.ui.notes.add.AddNoteScreen
import kotlinx.coroutines.CoroutineScope

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

        composable(Destinations.NOTES_ROUTE) {
            NotesScreen(onClickAddNote = { navActions.navigateToAddNoteScreen()})
        }

        composable(Destinations.ADD_NOTE_ROUTE) {
            AddNoteScreen(onBack = { navController.popBackStack()})
        }
    }

}