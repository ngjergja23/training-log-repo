package org.unizd.rma.traininglog.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.traininglog.domain.models.Trening
import org.unizd.rma.traininglog.domain.usecases.trening.AddTreningUseCase
import org.unizd.rma.traininglog.domain.usecases.trening.GetSingleTreningUseCase
import org.unizd.rma.traininglog.domain.usecases.trening.UpdateTreningUseCase
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTreningViewModel @Inject constructor(
    private val addTreningUseCase: AddTreningUseCase,
    private val updateTreningUseCase: UpdateTreningUseCase,
    private val getSingleTreningUseCase: GetSingleTreningUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AddTreningState>(AddTreningState.Idle)
    val state: StateFlow<AddTreningState> = _state.asStateFlow()

    private val _nazivVjezbe = MutableStateFlow("")
    val nazivVjezbe: StateFlow<String> = _nazivVjezbe.asStateFlow()

    private val _biljeska = MutableStateFlow("")
    val biljeska: StateFlow<String> = _biljeska.asStateFlow()

    private val _misicnaSkupina = MutableStateFlow("Noge")
    val misicnaSkupina: StateFlow<String> = _misicnaSkupina.asStateFlow()

    private val _datumTreninga = MutableStateFlow(Date())
    val datumTreninga: StateFlow<Date> = _datumTreninga.asStateFlow()

    private val _slikaUri = MutableStateFlow<String?>(null)
    val slikaUri: StateFlow<String?> = _slikaUri.asStateFlow()

    private var editingId: Int? = null

    fun setNazivVjezbe(value: String) { _nazivVjezbe.value = value}
    fun setBiljeska(value: String) { _biljeska.value = value }
    fun setMisicnaSkupina(value: String) { _misicnaSkupina.value = value }
    fun setDatumTreninga(value: Date) { _datumTreninga.value = value }
    fun setSlikaUri(value: String?) { _slikaUri.value = value }

    fun loadTreningForEdit(id: Int) {
        viewModelScope.launch {
            _state.value = AddTreningState.Loading
            val result = getSingleTreningUseCase(id)
            result.onSuccess { trening ->
                if (trening != null) {
                    editingId = trening.id  //
                    _nazivVjezbe.value = trening.nazivVjezbe
                    _biljeska.value = trening.biljeska
                    _misicnaSkupina.value = trening.misicnaSkupina
                    _datumTreninga.value = trening.datumTreninga
                    _slikaUri.value = trening.slikaUri
                }
                _state.value = AddTreningState.Idle
            }
            result.onFailure { exception ->
                _state.value = AddTreningState.Error(exception.message ?: "Greška pri dohvaćanju")
            }
        }
    }

    fun saveTrening() {
        if (_nazivVjezbe.value.isBlank()) {
            _state.value = AddTreningState.Error("Naziv vježbe je obavezan!")
            return
        }
        viewModelScope.launch {
            _state.value = AddTreningState.Loading

            val trening = Trening(
                id = editingId ?: 0,  //room ce staviti pravi id
                nazivVjezbe = _nazivVjezbe.value,
                biljeska = _biljeska.value,
                misicnaSkupina = _misicnaSkupina.value,
                datumTreninga = _datumTreninga.value,
                slikaUri = _slikaUri.value
            )

            val result = if (editingId != null) {
                updateTreningUseCase(trening)
            } else {
                addTreningUseCase(trening)
            }

            result.onSuccess {
                _state.value = AddTreningState.Success
            }
            result.onFailure { exception ->
                _state.value = AddTreningState.Error(exception.message ?: "Greška pri spremanju")
            }
        }
    }
}