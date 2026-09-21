package org.unizd.rma.traininglog.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val misicneSkupine = listOf("Noge", "Leđa", "Prsa", "Ruke", "Core", "Kardio", "Cijelo tijelo")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTreningScreen(
    viewModel: AddTreningViewModel = hiltViewModel(),
    treningId: Int? = null,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val nazivVjezbe by viewModel.nazivVjezbe.collectAsStateWithLifecycle()
    val biljeska by viewModel.biljeska.collectAsStateWithLifecycle()
    val misicnaSkupina by viewModel.misicnaSkupina.collectAsStateWithLifecycle()
    val datumTreninga by viewModel.datumTreninga.collectAsStateWithLifecycle()

    LaunchedEffect(treningId) {
        if (treningId != null) {
            viewModel.loadTreningForEdit(treningId)
        }
    }

    LaunchedEffect(state) {
        if (state is AddTreningState.Success) {
            onSaveSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (treningId != null) "Uredi trening" else "Dodaj trening") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Natrag")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (state is AddTreningState.Loading) {
                CircularProgressIndicator()
            }
            if (state is AddTreningState.Error) {
                Text(
                    text = (state as AddTreningState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            TextField(
                value = nazivVjezbe,
                onValueChange = { viewModel.setNazivVjezbe(it) },
                label = { Text("Naziv vježbe") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )
            TextField(
                value = biljeska,
                onValueChange = { viewModel.setBiljeska(it) },
                label = { Text("Bilješka") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            MisicnaSkupinaDropdown(
                selected = misicnaSkupina,
                onSelect = { viewModel.setMisicnaSkupina(it) }
            )

            DatumPicker(
                selectedDate = datumTreninga,
                onDateSelected = { viewModel.setDatumTreninga(it) }
            )

            Button(
                onClick = { viewModel.saveTrening() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text("Spremi")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisicnaSkupinaDropdown(selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Mišićna skupina") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            misicneSkupine.forEach { skupina ->
                DropdownMenuItem(
                    text = { Text(skupina) },
                    onClick = {
                        onSelect(skupina)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DatumPicker(selectedDate: Date, onDateSelected: (Date) -> Unit) {
    val formatter = remember { SimpleDateFormat("dd.MM.yyyy.", Locale("hr")) }
    TextField(
        value = formatter.format(selectedDate),
        onValueChange = {},
        readOnly = true,
        label = { Text("Datum treninga") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}