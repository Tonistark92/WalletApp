package com.iscoding.designsystem.componenets.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun WalletPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

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
            singleLine = true,
            isError = isError,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Rounded.VisibilityOff
                        } else {
                            Icons.Rounded.Visibility
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            },
            shape = WalletAppTheme.shapes.medium,
            textStyle = WalletAppTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WalletAppTheme.colors.primary,
                unfocusedBorderColor = WalletAppTheme.colors.outline,
                errorBorderColor = WalletAppTheme.colors.error,
                focusedLabelColor = WalletAppTheme.colors.primary,
                unfocusedLabelColor = WalletAppTheme.colors.onSurfaceVariant,
                errorLabelColor = WalletAppTheme.colors.error,
                cursorColor = WalletAppTheme.colors.primary,
            )
        )

        PasswordRequirements(
            password = value
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

@Composable
private fun PasswordRequirements(
    password: String,
) {
    Column(
        modifier = Modifier.padding(
            top = WalletAppTheme.spacing.small
        )
    ) {
        PasswordRequirement(
            text = "At least 8 characters",
            satisfied = password.length >= 8
        )

        PasswordRequirement(
            text = "One uppercase letter",
            satisfied = password.any { it.isUpperCase() }
        )

        PasswordRequirement(
            text = "One lowercase letter",
            satisfied = password.any { it.isLowerCase() }
        )

        PasswordRequirement(
            text = "One number",
            satisfied = password.any { it.isDigit() }
        )

        PasswordRequirement(
            text = "One special character",
            satisfied = password.any {
                !it.isLetterOrDigit()
            }
        )
    }
}
@Composable
private fun PasswordRequirement(
    text: String,
    satisfied: Boolean,
) {
    Text(
        text = if (satisfied) "✓ $text" else "• $text",
        style = WalletAppTheme.typography.bodySmall,
        color = if (satisfied) {
            WalletAppTheme.colors.success
        } else {
            WalletAppTheme.colors.onSurfaceVariant
        }
    )
}

@Preview(
    name = "Password Field",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WalletPasswordFieldPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletPasswordField(
            value = "Password123",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Password Field - Error",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WalletPasswordFieldErrorPreview() {
    WalletAppTheme(useDarkTheme = false) {
        WalletPasswordField(
            value = "weak",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            isError = true,
            errorMessage = "Password does not meet the requirements"
        )
    }
}