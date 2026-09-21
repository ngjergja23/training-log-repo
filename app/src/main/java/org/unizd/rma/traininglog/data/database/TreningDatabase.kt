package org.unizd.rma.traininglog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.unizd.rma.traininglog.data.database.dao.TreningDao
import org.unizd.rma.traininglog.data.database.entity.TreningEntity

@Database(
    entities = [TreningEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TreningDatabase : RoomDatabase() {
    abstract fun treningDao(): TreningDao

    companion object {
        const val DATABASE_NAME = "training_db"
    }
}