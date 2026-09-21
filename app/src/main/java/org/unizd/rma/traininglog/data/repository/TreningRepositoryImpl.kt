package org.unizd.rma.traininglog.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.unizd.rma.traininglog.data.database.dao.TreningDao
import org.unizd.rma.traininglog.data.mappers.toDomain
import org.unizd.rma.traininglog.data.mappers.toEntity
import org.unizd.rma.traininglog.domain.models.Trening
import org.unizd.rma.traininglog.domain.repositories.TreningRepository

class TreningRepositoryImpl(private val treningDao: TreningDao) : TreningRepository {
    override fun getAllTreninzi(): Flow<List<Trening>> {
        return treningDao.getAllTreninzi().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getSingleTrening(id: Int): Trening? {
        return treningDao.getTreningById(id)?.toDomain()
    }

    override suspend fun addNewTrening(trening: Trening): Long {
        return treningDao.insertTrening(trening.toEntity())
    }

    override suspend fun updateTrening(trening: Trening) {
        return treningDao.updateTrening(trening.toEntity())
    }

    override suspend fun deleteTrening(id: Int) {
        return treningDao.deleteTreningById(id)
    }

}