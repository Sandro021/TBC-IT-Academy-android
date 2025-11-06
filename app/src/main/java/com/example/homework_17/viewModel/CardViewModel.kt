package com.example.homework_17.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.model.CardInfo
import com.example.homework_17.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = CardRepository(app)

    private val _cards = MutableStateFlow<List<CardInfo>>(emptyList())
    val cards = _cards.asStateFlow()

    init {
        viewModelScope.launch {
            _cards.value = repo.loadCards()
        }
    }

    fun addCard(card: CardInfo) {
        viewModelScope.launch {
            val updated = _cards.value + card
            _cards.value = updated
            repo.saveCards(updated)
        }
    }
}