package org.example.volunteer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.volunteer.presentation.screens.mainevent.components.EventSearchBar

@Preview
@Composable
private fun EventSearchBarPreview(){
    MaterialTheme {
        EventSearchBar(
            searchQuery = "akokfsop",
            onSearchQueryChanged = {},
            modifier = Modifier
                .fillMaxWidth(  )
        )
    }
}