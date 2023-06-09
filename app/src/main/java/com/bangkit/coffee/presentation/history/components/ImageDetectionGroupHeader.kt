package com.bangkit.coffee.presentation.history.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.shared.theme.AppTheme
import com.bangkit.coffee.shared.util.toDateString
import java.time.LocalDate

@Composable
fun ImageDetectionGroupHeader(
    modifier: Modifier = Modifier,
    date: LocalDate
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.CalendarMonth,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = date.toDateString(),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(name = "ImageDetectionGroupHeader", showBackground = true)
@Composable
private fun PreviewImageDetectionGroupHeader() {
    AppTheme {
        ImageDetectionGroupHeader(
            date = LocalDate.now()
        )
    }
}