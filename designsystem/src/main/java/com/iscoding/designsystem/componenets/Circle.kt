package com.iscoding.designsystem.componenets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun CircleInBack(
    modifier: Modifier = Modifier,
    color: Color,
    border: BorderStroke= BorderStroke(0.dp, Color.Transparent),
    content: @Composable () -> Unit
) {
    Surface(
        shape = WalletAppTheme.shapes.full,
        color = color,
        border = border,
        modifier = modifier
    ) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

}