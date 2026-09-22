package org.unizd.rma.traininglog

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.unizd.rma.traininglog.data.database.TreningDatabase
import org.unizd.rma.traininglog.data.database.dao.TreningDao
import org.unizd.rma.traininglog.data.repository.TreningRepositoryImpl
import org.unizd.rma.traininglog.domain.repositories.TreningRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTreningDatabase(
        @ApplicationContext context: Context
    ): TreningDatabase {
        return Room.databaseBuilder(  //
            context,
            TreningDatabase::class.java,
            TreningDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTreningDao(
        database: TreningDatabase
    ): TreningDao {
        return database.treningDao()
    }

    @Singleton
    @Provides
    fun provideTreningRepository(
        dao: TreningDao
    ): TreningRepository {
        return TreningRepositoryImpl(dao)
    }
}