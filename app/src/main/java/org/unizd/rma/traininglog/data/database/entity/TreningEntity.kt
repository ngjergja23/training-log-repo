package org.unizd.rma.traininglog.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("treninzi")
data class TreningEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nazivVjezbe: String,
    val biljeska: String = "",
    val misicnaSkupina: String,
    val datumTreninga: Date,
    val slikaUri: String? = null
)