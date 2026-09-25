package com.app.mazaayaa.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.mazaayaa.core.model.Dictionary
import com.app.mazaayaa.core.model.FilterType
import com.app.mazaayaa.core.repository.DictionaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val items: List<Dictionary> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: FilterType = FilterType.ALL,
    val isLoading: Boolean = false
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DictionaryRepository(application.applicationContext)

    private val _searchQuery = MutableStateFlow("")
    private val _selectedFilter = MutableStateFlow(FilterType.ALL)
    private val _rawList = MutableStateFlow<List<Dictionary>>(emptyList())
    private val _isLoading = MutableStateFlow(true)

    val uiState: StateFlow<HomeUiState> = combine(
        _rawList,
        _searchQuery,
        _selectedFilter,
        _isLoading
    ) { items, query, filter, loading ->
        val filtered = items.filter { item ->
            val matchFilter = when (filter) {
                FilterType.ALL -> true
                FilterType.ISM -> item.type.equals("ism", ignoreCase = true)
                FilterType.FIIL -> item.type.equals("fiil", ignoreCase = true)
            }
            matchFilter
        }
        HomeUiState(
            items = filtered,
            searchQuery = query,
            selectedFilter = filter,
            isLoading = loading
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _rawList.value = repository.getAllDictionary()
            _isLoading.value = false
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        viewModelScope.launch {
            _rawList.value = if (newQuery.isBlank()) {
                repository.getAllDictionary()
            } else {
                repository.searchDictionary(newQuery)
            }
        }
    }

    fun onFilterSelected(filter: FilterType) {
        _selectedFilter.value = filter
    }
}