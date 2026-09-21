package org.unizd.rma.traininglog.data.mappers

import org.unizd.rma.traininglog.data.database.entity.TreningEntity
import org.unizd.rma.traininglog.domain.models.Trening

fun TreningEntity.toDomain(): Trening = Trening(
    id = id,
    nazivVjezbe = nazivVjezbe,
    biljeska = biljeska,
    misicnaSkupina = misicnaSkupina,
    datumTreninga = datumTreninga,
    slikaUri = slikaUri
)

fun Trening.toEntity(): TreningEntity = TreningEntity(
    id = id,
    nazivVjezbe = nazivVjezbe,
    biljeska = biljeska,
    misicnaSkupina = misicnaSkupina,
    datumTreninga = datumTreninga,
    slikaUri = slikaUri
)