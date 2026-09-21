package org.unizd.rma.traininglog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.unizd.rma.traininglog.presentation.create.AddTreningScreen
import org.unizd.rma.traininglog.presentation.list.TreningListScreen

@Composable
fun TrainingLogNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TreningList.route
    ) {
        composable(Screen.TreningList.route) {
            TreningListScreen(
                onTreningClick = { id ->
                    navController.navigate(Screen.EditTrening.createRoute(id))
                },
                onAddClick = {
                    navController.navigate(Screen.AddTrening.route)
                }
            )
        }

        composable(Screen.AddTrening.route) {
            AddTreningScreen(
                treningId = null,
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditTrening.route,
            arguments = listOf(navArgument("treningId") { type = NavType.IntType })
        ) { backStackEntry ->
            val treningId = backStackEntry.arguments?.getInt("treningId")
            AddTreningScreen(
                treningId = treningId,
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}