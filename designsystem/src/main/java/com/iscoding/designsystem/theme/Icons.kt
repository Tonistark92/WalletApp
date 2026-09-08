package com.iscoding.designsystem.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.staticCompositionLocalOf

object WalletAppIcons {
    val Add = Icons.Rounded.Add
    val Delete = Icons.Rounded.Delete
}

internal val LocalWalletAppIcons = staticCompositionLocalOf { WalletAppIcons }