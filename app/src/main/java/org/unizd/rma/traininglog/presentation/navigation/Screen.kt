package org.unizd.rma.traininglog.presentation.navigation

sealed class Screen(val route: String) {
    object TreningList : Screen("trening_list")

    object AddTrening : Screen("add_trening")

    object EditTrening : Screen("edit_trening/{treningId}") {
        fun createRoute(treningId: Int) = "edit_trening/$treningId"
    }
}