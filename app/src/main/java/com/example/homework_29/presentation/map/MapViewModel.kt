package com.example.homework_29.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_29.domain.usecase.ObserveLocationsUseCase
import com.example.homework_29.domain.usecase.RefreshLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val observeLocations: ObserveLocationsUseCase,
    private val refreshLocations: RefreshLocationsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MapEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            observeLocations().collect { list ->
                _state.update { it.copy(locations = list) }
            }
        }
    }

    fun process(intent: MapIntent) {
        when (intent) {
            is MapIntent.ScreenOpened -> onScreenOpened()
            is MapIntent.PermissionGranted -> onPermissionGranted()
            is MapIntent.PermissionDenied -> onPermissionDenied()
            is MapIntent.BottomSheetDismissed -> _state.update { it.copy(selected = null) }
            is MapIntent.ZoomIn -> {}
            is MapIntent.ZoomOut -> {}
            is MapIntent.MarkerClicked -> _state.update { it.copy(selected = intent.item) }
        }
    }

    private fun onScreenOpened() {
        viewModelScope.launch { _effect.emit(MapEffect.RequestPermission) }
    }

    private fun onPermissionGranted() {
        _state.update { it.copy(hasPermission = true, isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            _effect.emit(MapEffect.MoveCameraToMyLocation)
            runCatching { refreshLocations() }
                .onFailure {

                    _state.update { s ->
                        s.copy(
                            errorMessage = "Could not download. Showing offline data.",
                            isLoading = false
                        )
                    }
                    _effect.emit(MapEffect.ShowError("Could not download. Showing offline data."))
                }
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun onPermissionDenied() {
        _state.update {
            it.copy(
                hasPermission = false,
                errorMessage = "Location permission is required"
            )
        }
    }
}