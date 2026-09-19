package com.iscoding.designsystem.componenets.bottom_nav

import androidx.compose.ui.graphics.vector.ImageVector

data class WalletNavItem(
    val destination: WalletDestination,
    val label: String,
    val icon: ImageVector
)