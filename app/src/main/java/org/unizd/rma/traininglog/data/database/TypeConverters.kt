package org.unizd.rma.traininglog.data.database

import androidx.room.TypeConverter
import java.util.Date

//Date pretvori u Long (broj milisekundi) za spremanje,
// i obrnuto kad ga čitaš natrag

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}