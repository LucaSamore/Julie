package com.example.julie.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun InterestModalBottomSheet(onDismiss: () -> Unit, modifier: Modifier) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        val category =
            listOf(
                Pair(
                    "Category 1",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 2",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 3",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 4",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 5",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
            )

        LazyColumn {
            category.forEach {
                stickyHeader {
                    Row(
                        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = it.first)
                    }
                }

                items(it.second) { interest ->
                    Row(
                        modifier = modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = interest)
                    }
                }
            }
        }
    }
}
