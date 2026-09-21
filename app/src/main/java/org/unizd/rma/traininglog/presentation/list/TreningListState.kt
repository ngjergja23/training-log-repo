package org.unizd.rma.traininglog.presentation.list

import org.unizd.rma.traininglog.domain.models.Trening

sealed class TreningListState {
    object Loading : TreningListState()
    data class Success(val treninzi: List<Trening>) : TreningListState()
    data class Error(val message: String) : TreningListState()
    object Empty : TreningListState()
}