package com.iscoding.designsystem.componenets.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme


@Composable
fun WalletOtpField(
    value: String,
    onValueChange: (String) -> Unit,
    onComplete: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    errorMessage: String? = null,
) {
    var focused by remember { mutableStateOf(false) }

    val focusRequester = remember {
        FocusRequester()
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (
                    newValue.length <= length &&
                    newValue.all(Char::isDigit)
                ) {
                    onValueChange(newValue)
                    if (newValue.length == length) {
                        onComplete(newValue)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    focused = it.isFocused
                },
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            decorationBox = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        WalletAppTheme.spacing.small
                    )
                ) {
                    repeat(length) { index ->
                        OtpCell(
                            value = value.getOrNull(index),
                            isFocused = index == value.length && focused,
                            isError = isError,
                            isSuccess = isSuccess,
                        )
                    }
                }
            }
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(
                    top = WalletAppTheme.spacing.small
                ),
                style = WalletAppTheme.typography.bodySmall,
                color = WalletAppTheme.colors.error
            )
        }
    }
}

@Composable
private fun OtpCell(
    value: Char?,
    isFocused: Boolean,
    isError: Boolean,
    isSuccess: Boolean,
) {
    val borderColor = when {
        isSuccess -> WalletAppTheme.colors.success

        isError -> WalletAppTheme.colors.error

        isFocused -> WalletAppTheme.colors.primary

        else -> WalletAppTheme.colors.outline
    }

    Box(
        modifier = Modifier
            .size(45.dp)
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = WalletAppTheme.shapes.small
            ),
        contentAlignment = Alignment.Center
    ) {
        if (value != null) {
            Text(
                text = value.toString(),
                style = WalletAppTheme.typography.titleLarge,
                color = WalletAppTheme.colors.onSurface
            )
        }
    }
}
@Preview(
    name = "OTP - Light",
    showBackground = true,
    widthDp = 360,
    heightDp = 700
)
@Composable
private fun WalletOtpFieldLightPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletOtpPreviewContent()
    }
}

@Preview(
    name = "OTP - Dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 700
)
@Composable
private fun WalletOtpFieldDarkPreview() {
    WalletAppTheme(useDarkTheme = true) {
        WalletOtpPreviewContent()
    }
}

@Composable
private fun WalletOtpPreviewContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WalletAppTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(
            WalletAppTheme.spacing.extraLarge
        )
    ) {
        Text(
            text = "Verify your phone",
            style = WalletAppTheme.typography.headlineMedium
        )

        Text(
            text = "Enter the 6-digit code sent to your phone.",
            style = WalletAppTheme.typography.bodyMedium,
            color = WalletAppTheme.colors.onSurfaceVariant
        )

        WalletOtpField(
            value = "123",
            onValueChange = {},
            onComplete = {}
        )

        WalletOtpField(
            value = "123456",
            onValueChange = {},
            isError = true,
            errorMessage = "Invalid verification code",
            onComplete = {}


        )

        WalletOtpField(
            value = "123456",
            onValueChange = {},
            isSuccess = true,
            onComplete = {}

        )
    }
}