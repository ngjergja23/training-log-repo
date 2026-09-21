package org.unizd.rma.traininglog.domain.usecases.trening

import kotlinx.coroutines.CancellationException
import org.unizd.rma.traininglog.domain.repositories.TreningRepository
import javax.inject.Inject

class DeleteTreningUseCase @Inject constructor(
    private val repository: TreningRepository
){
    suspend operator fun invoke(id: Int): Result<Unit> {
        return try {
            repository.deleteTrening(id)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}