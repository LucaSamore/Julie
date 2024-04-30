package com.example.julie.smartphoneusage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun SmartphoneUsageScreen(
    modifier: Modifier,
    smartphoneUsageViewModel: SmartphoneUsageViewModel,
    paddingValues: PaddingValues
) {
    val state by smartphoneUsageViewModel.smartphoneUsageScreenState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Smartphone usage screen")
    }
}