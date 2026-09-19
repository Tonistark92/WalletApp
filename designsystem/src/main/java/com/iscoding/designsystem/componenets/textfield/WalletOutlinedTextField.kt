package com.iscoding.designsystem.componenets.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun WalletOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingContent: (@Composable (() -> Unit))? = null,
    trailingContent: (@Composable (() -> Unit))? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = label)
            },
            placeholder = placeholder?.let {
                {
                    Text(text = it)
                }
            },
            leadingIcon = leadingContent,
            trailingIcon = trailingContent,
            enabled = enabled,
            singleLine = singleLine,
            isError = isError,
            keyboardOptions = keyboardOptions,
            shape = WalletAppTheme.shapes.medium,
            textStyle = WalletAppTheme.typography.bodyMedium,
            colors = walletOutlinedTextFieldColors(),
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(
                    horizontal = WalletAppTheme.spacing.small,
                    vertical = WalletAppTheme.spacing.extraSmall
                ),
                style = WalletAppTheme.typography.bodySmall,
                color = WalletAppTheme.colors.error
            )
        }
    }
}
//
//WalletOutlinedTextField(
//value = phone,
//onValueChange = onPhoneChanged,
//label = "Phone number",
//isError = true,
//errorMessage = "Invalid phone number",
//trailingIcon = {
//    if (trailingContent != null) {
//        trailingContent()
//    } else if (isError) {
//        Icon(
//            imageVector = WalletAppTheme.icons.error,
//            contentDescription = null,
//            tint = WalletAppTheme.colors.error
//        )
//    }
//}
//)


@Preview(
    name = "Outlined Text Field - Light",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WalletOutlinedTextFieldLightPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletOutlinedTextField(
            value = "01012345678",
            onValueChange = {},
            label = "Phone number",
            placeholder = "Enter your phone number",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Outlined Text Field - Error",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WalletOutlinedTextFieldErrorPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletOutlinedTextField(
            value = "010123",
            onValueChange = {},
            label = "Phone number",
            modifier = Modifier.padding(16.dp),
            isError = true,
            errorMessage = "Please enter a valid phone number"
        )
    }
}