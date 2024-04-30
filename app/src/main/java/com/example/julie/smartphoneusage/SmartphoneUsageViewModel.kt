package com.example.julie.smartphoneusage

import androidx.lifecycle.ViewModel
import com.example.julie.Lce
import com.example.julie.SmartphoneUsageScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SmartphoneUsageScreenContent

@HiltViewModel
internal class SmartphoneUsageViewModel @Inject constructor(

): ViewModel() {

    private val _smartphoneUsageScreenState = MutableStateFlow<SmartphoneUsageScreenState>(value = Lce.Loading)
    val smartphoneUsageScreenState = _smartphoneUsageScreenState.asStateFlow()


}