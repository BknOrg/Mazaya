package com.app.mazaayaa.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.mazaayaa.core.model.FilterType
import com.app.mazaayaa.ui.home.components.DictionaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Mazaayaa") },
                windowInsets = TopAppBarDefaults.windowInsets
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Cari kosa kata (Arab/Indonesia)...") },
                singleLine = true
            )

            // Filter Chips (Semua, Isim, Fi'il)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterType.entries.forEach { filter ->
                    FilterChip(
                        selected = state.selectedFilter == filter,
                        onClick = { viewModel.onFilterSelected(filter) },
                        label = { Text(filter.label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Konten / List
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(items = state.items, key = { "${it.type}_${it.id}" }) { item ->
                        DictionaryCard(item = item)
                    }
                }
            }
        }
    }
}