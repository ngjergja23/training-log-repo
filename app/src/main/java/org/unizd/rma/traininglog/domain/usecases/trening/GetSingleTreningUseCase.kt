package org.unizd.rma.traininglog.domain.usecases.trening

import kotlinx.coroutines.CancellationException
import org.unizd.rma.traininglog.domain.models.Trening
import org.unizd.rma.traininglog.domain.repositories.TreningRepository
import javax.inject.Inject

class GetSingleTreningUseCase @Inject constructor(
    private val repository: TreningRepository
) {
    suspend operator fun invoke(id: Int): Result<Trening?> {
        return try {
            Result.success(repository.getSingleTrening(id))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}