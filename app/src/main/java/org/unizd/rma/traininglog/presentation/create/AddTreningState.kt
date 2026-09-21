package org.unizd.rma.traininglog.presentation.create

sealed class AddTreningState {
    object Idle : AddTreningState()
    object Loading : AddTreningState()
    object Success : AddTreningState()
    data class Error(val message: String) : AddTreningState()
}