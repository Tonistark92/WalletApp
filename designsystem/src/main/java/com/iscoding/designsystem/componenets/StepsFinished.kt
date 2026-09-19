package com.iscoding.designsystem.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun OnboardingProgress(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {

        Text(
            text = "Step $currentStep of $totalSteps",
            style = WalletAppTheme.typography.labelSmall,
            color = WalletAppTheme.colors.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(
                WalletAppTheme.spacing.small
            )
        )

        LinearProgressIndicator(
            progress = {
                currentStep.toFloat() / totalSteps
            },
            modifier = Modifier.fillMaxWidth().height(6. dp),
            color = WalletAppTheme.colors.primary,
            trackColor = WalletAppTheme.colors.surfaceVariant,
        )
    }
}

@Preview(
    name = "Onboarding Progress Review",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun OnboardingProgressReviewPreview() {
    WalletAppTheme(useDarkTheme = false) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WalletAppTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(
                WalletAppTheme.spacing.extraLarge
            )
        ) {

            // Design 1
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    WalletAppTheme.spacing.medium
                ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Design 1 — Segmented",
                    style = WalletAppTheme.typography.titleMedium,
                )

                OnboardingProgress(
                    currentStep = 1,
                    totalSteps = 3
                )
            }

        }
    }
}


//@Composable
//fun OnboardingLinearProgress2(
//    currentStep: Int,
//    totalSteps: Int,
//    modifier: Modifier = Modifier
//) {
//    val progress = currentStep.toFloat() / totalSteps
//
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(4.dp)
//            .clip(WalletAppTheme.shapes.full)
//            .background(
//                WalletAppTheme.colors.surfaceVariant
//            )
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth(progress)
//                .fillMaxHeight()
//                .background(
//                    WalletAppTheme.colors.primary
//                )
//        )
//    }
//}