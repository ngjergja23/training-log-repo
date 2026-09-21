package org.unizd.rma.traininglog.domain.usecases.trening

import org.unizd.rma.traininglog.domain.models.Trening
import org.unizd.rma.traininglog.domain.repositories.TreningRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UpdateTreningUseCase @Inject constructor(
    private val repository: TreningRepository
){
    suspend operator fun invoke (trening: Trening): Result<Unit> {
        return try {
            repository.updateTrening(trening)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}