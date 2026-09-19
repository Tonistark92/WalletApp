package com.iscoding.designsystem.componenets.textfield

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun walletOutlinedTextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = WalletAppTheme.colors.primary,
        unfocusedBorderColor = WalletAppTheme.colors.outline,
        errorBorderColor = WalletAppTheme.colors.error,
        focusedLabelColor = WalletAppTheme.colors.primary,
        unfocusedLabelColor = WalletAppTheme.colors.onSurfaceVariant,
        errorLabelColor = WalletAppTheme.colors.error,
        cursorColor = WalletAppTheme.colors.primary,
    )