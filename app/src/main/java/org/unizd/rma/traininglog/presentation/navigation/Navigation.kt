package org.unizd.rma.traininglog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.unizd.rma.traininglog.presentation.camera.CameraScreen
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

        composable(Screen.AddTrening.route) { backStackEntry ->
            val photoUri by backStackEntry.savedStateHandle
                .getStateFlow<String?>("captured_photo_uri", null)
                .collectAsState()

            AddTreningScreen(
                treningId = null,
                capturedPhotoUri = photoUri,
                onPhotoClick = { navController.navigate(Screen.Camera.route) },
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditTrening.route,
            arguments = listOf(navArgument("treningId") { type = NavType.IntType })
        ) { backStackEntry ->
            val treningId = backStackEntry.arguments?.getInt("treningId")
            val photoUri by backStackEntry.savedStateHandle
                .getStateFlow<String?>("captured_photo_uri", null)
                .collectAsState()

            AddTreningScreen(
                treningId = treningId,
                capturedPhotoUri = photoUri,
                onPhotoClick = { navController.navigate(Screen.Camera.route) },
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }

        composable(Screen.Camera.route) {
            CameraScreen(
                onPhotoTaken = { uri ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("captured_photo_uri", uri)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}