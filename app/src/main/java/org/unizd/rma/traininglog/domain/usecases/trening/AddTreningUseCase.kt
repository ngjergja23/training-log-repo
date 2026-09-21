package org.unizd.rma.traininglog.domain.usecases.trening

import kotlinx.coroutines.CancellationException
import org.unizd.rma.traininglog.domain.models.Trening
import org.unizd.rma.traininglog.domain.repositories.TreningRepository
import javax.inject.Inject

class AddTreningUseCase @Inject constructor(
    private val repository: TreningRepository
) {
    suspend operator fun invoke(trening: Trening): Result<Long> {
        return try {
            Result.success(repository.addNewTrening(trening))
        } catch (e: CancellationException) {
            throw e   //otkazuje corutine
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}