package org.unizd.rma.traininglog.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.unizd.rma.traininglog.domain.models.Trening

@Composable
fun TreningListScreen(
    viewModel: TreningListViewModel = hiltViewModel(),
    onTreningClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, contentDescription = "Dodaj trening")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val currentState = state) {
                is TreningListState.Loading -> {
                    CircularProgressIndicator()
                }
                is TreningListState.Empty -> {
                    Text("Unesi prvi trening!")
                }
                is TreningListState.Success -> {
                    TreningList(
                        treninzi = currentState.treninzi,
                        onTreningClick = onTreningClick,
                        onDeleteClick = { id -> viewModel.deleteTrening(id) }
                    )
                }
                is TreningListState.Error -> {
                    Text(text = "Greška: ${currentState.message}")
                }
            }
        }
    }

}

@Composable
fun TreningList(
    treninzi: List<Trening>,
    onTreningClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(treninzi) { trening ->
            TreningListItem(
                trening = trening,
                onClick = { onTreningClick(trening.id) },
                onDeleteClick = { onDeleteClick(trening.id) }
            )
        }
    }
}

@Composable
fun TreningListItem(
    trening: Trening,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable  {onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Text(
                    text = trening.nazivVjezbe,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = trening.misicnaSkupina,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Filled.Delete, contentDescription = "Obriši")
            }
        }
    }
}










