package com.example.julie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun AppReactions(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(.9f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = modifier.padding(horizontal = 16.dp).size(48.dp, 48.dp))

        Box(
            modifier =
                modifier
                    .width(72.dp)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
                    .background(Color(0xFFE6E5EB))
        ) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "❤", modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                Text(text = "123", modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp))
            }
        }

        Spacer(modifier = modifier.padding(horizontal = 8.dp))

        Box(
            modifier =
                modifier
                    .width(72.dp)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
                    .background(Color(0xFFE6E5EB))
        ) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "💬", modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                Text(text = "123", modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp))
            }
        }
    }
}
