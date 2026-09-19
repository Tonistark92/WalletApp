package com.iscoding.designsystem.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme


@Composable
fun WalletCard(
    modifier: Modifier = Modifier,
) {
    val WalletPrimaryGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF4169F6),
            Color(0xFF0040DF),
            Color(0xFF002F9E)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )
    Card(
        modifier = modifier,
        shape = WalletAppTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = WalletPrimaryGradient,
                    shape = WalletAppTheme.shapes.large
                )
                .padding(WalletAppTheme.spacing.large).size(200.dp),

            ) {
        }
    }
}
