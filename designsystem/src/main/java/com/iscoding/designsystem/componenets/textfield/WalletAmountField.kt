package com.iscoding.designsystem.componenets.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun WalletAmountField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Amount",
    currency: String = "EGP",
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    WalletOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        isError = isError,
        errorMessage = errorMessage,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        trailingContent = {
            Text(
                text = currency,
                style = WalletAppTheme.typography.bodyMedium,
                color = WalletAppTheme.colors.onSurfaceVariant
            )
        }
    )
}


@Preview(
    name = "Amount Field",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WalletAmountFieldPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletAmountField(
            value = "1,500.00",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            currency = "EGP"
        )
    }
}

@Preview(
    name = "Amount Field - Error",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WalletAmountFieldErrorPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletAmountField(
            value = "0",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            currency = "EGP",
            isError = true,
            errorMessage = "Amount must be greater than 0"
        )
    }
}