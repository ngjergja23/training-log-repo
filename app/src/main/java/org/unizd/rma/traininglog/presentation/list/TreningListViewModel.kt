package org.unizd.rma.traininglog.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.unizd.rma.traininglog.domain.usecases.trening.DeleteTreningUseCase
import org.unizd.rma.traininglog.domain.usecases.trening.GetTreninziUseCase
import javax.inject.Inject

@HiltViewModel
class TreningListViewModel @Inject constructor(
    private val getTreninziUseCase: GetTreninziUseCase,
    private val deleteTreningUseCase: DeleteTreningUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TreningListState>(TreningListState.Loading)
    val state: StateFlow<TreningListState> = _state


    init {
        loadTreninzi()
    }

    private fun loadTreninzi() {
        viewModelScope.launch {
            getTreninziUseCase()
                .catch { e ->
                    _state.value = TreningListState.Error(e.message ?: "Greška pri dohvaćanju")
                }
                .collect { treninzi ->
                    _state.value = if (treninzi.isEmpty()) {
                        TreningListState.Empty
                    } else {
                        TreningListState.Success(treninzi)
                    }
                }
        }
    }

    fun deleteTrening(id: Int) {
        viewModelScope.launch {
            deleteTreningUseCase(id)
        }
    }
}