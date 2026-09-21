package org.unizd.rma.traininglog.domain.models

import java.util.Date

data class Trening(
    val id: Int = 0,
    val nazivVjezbe: String,
    val biljeska: String = "",
    val misicnaSkupina: String,
    val datumTreninga: Date,
    val slikaUri: String? = null
)
