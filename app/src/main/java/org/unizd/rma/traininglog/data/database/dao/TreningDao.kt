package org.unizd.rma.traininglog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.unizd.rma.traininglog.data.database.entity.TreningEntity

@Dao
interface TreningDao {

    @Query("SELECT * FROM treninzi ORDER BY datumTreninga DESC")
    fun getAllTreninzi(): Flow<List<TreningEntity>>

    @Query("SELECT * FROM treninzi WHERE id = :id")
    suspend fun getTreningById(id: Int): TreningEntity?

    @Insert
    suspend fun insertTrening(treningEntity: TreningEntity): Long // rowId

    @Update
    suspend fun updateTrening(treningEntity: TreningEntity)

    @Query("DELETE FROM treninzi WHERE id = :id")
    suspend fun deleteTreningById(id: Int)
}