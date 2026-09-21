package org.unizd.rma.traininglog.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.unizd.rma.traininglog.domain.models.Trening

interface TreningRepository {
    fun getAllTreninzi(): Flow<List<Trening>>
    suspend fun getSingleTrening(id: Int): Trening?
    suspend fun addNewTrening(trening: Trening): Long
    suspend fun updateTrening(trening: Trening)
    suspend fun deleteTrening(id: Int)
}