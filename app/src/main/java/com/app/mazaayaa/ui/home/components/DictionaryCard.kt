package com.app.mazaayaa.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.mazaayaa.core.model.Dictionary
import com.app.mazaayaa.ui.theme.ArabicTextStyle

@Composable
fun DictionaryCard(
    item: Dictionary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Sisi Kiri: Arti Bahasa Indonesia & Badge Tipe
            Column(modifier = Modifier.weight(1f)) {
                SuggestionChip(
                    onClick = {},
                    label = {
                        Text(
                            text = item.type.uppercase(),
                            fontSize = 11.sp
                        )
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.means,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Sisi Kanan: Teks Arab dengan Font Scheherazade
            Text(
                text = item.words,
                style = ArabicTextStyle,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}