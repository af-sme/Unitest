package de.appsfactory.unitest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.appsfactory.unitest.domain.entity.Match
import de.appsfactory.unitest.domain.interactor.GetMatchesUseCase
import kotlinx.coroutines.launch

class MatchesViewModel(private val getMatchesUseCase: GetMatchesUseCase) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _matches = MutableLiveData<List<Match>>()
    val matches: LiveData<List<Match>>
        get() = _matches

    fun onRefresh() {
        viewModelScope.launch {
            val result = getMatchesUseCase.execute()
            result.getOrNull()?.let {
                _matches.value = it
            } ?: run {
                _error.value = result.exceptionOrNull()?.localizedMessage
                    ?: "Unknown error"
            }
        }
    }

}