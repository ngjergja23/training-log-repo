package org.unizd.rma.traininglog.domain.usecases.trening

import kotlinx.coroutines.flow.Flow
import org.unizd.rma.traininglog.domain.models.Trening
import org.unizd.rma.traininglog.domain.repositories.TreningRepository
import javax.inject.Inject

class GetTreninziUseCase @Inject constructor(
    private val repository: TreningRepository
){
    operator fun invoke(): Flow<List<Trening>> {
        return repository.getAllTreninzi()
    }

    //any additional business logic here
}