package com.iscoding.designsystem.componenets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.iscoding.designsystem.componenets.bottom_nav.WalletDestination
import com.iscoding.designsystem.componenets.bottom_nav.WalletNavItem
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun WalletBottomNavigation(
    selectedDestination: WalletDestination,
    onDestinationClick: (WalletDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = WalletAppTheme.colors.surface,
    ) {
        val walletNavItems = listOf(
            WalletNavItem(
                destination = WalletDestination.Home,
                label = "Home",
                icon = WalletAppTheme.icons.NavWallet
            ),
            WalletNavItem(
                destination = WalletDestination.Transfer,
                label = "Send",
                icon = WalletAppTheme.icons.NavSend
            ),
            WalletNavItem(
                destination = WalletDestination.TopUp,
                label = "Top Up",
                icon = WalletAppTheme.icons.NavTopUp
            ),
            WalletNavItem(
                destination = WalletDestination.Activity,
                label = "Activity",
                icon = WalletAppTheme.icons.NAVActivity
            ),
            WalletNavItem(
                destination = WalletDestination.Cards,
                label = "Cards",
                icon = WalletAppTheme.icons.NavCard
            )
        )
        walletNavItems.forEach { item ->

            NavigationBarItem(
                selected = selectedDestination == item.destination,
                onClick = {
                    onDestinationClick(item.destination)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = WalletAppTheme.typography.labelSmall,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WalletAppTheme.colors.primary,
                    selectedTextColor = WalletAppTheme.colors.primary,
                    unselectedIconColor = WalletAppTheme.colors.onSurfaceVariant,
                    unselectedTextColor = WalletAppTheme.colors.onSurfaceVariant,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}


@Preview(
    name = "Light",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun WalletBottomNavigationLightPreview() {
    WalletAppTheme(useDarkTheme = false) {

        WalletBottomNavigation(
            selectedDestination = WalletDestination.Home,
            onDestinationClick = {}
        )
    }
}

@Preview(
    name = "Dark",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun WalletBottomNavigationDarkPreview() {
    WalletAppTheme(useDarkTheme = true) {

        WalletBottomNavigation(
            selectedDestination = WalletDestination.Home,
            onDestinationClick = {}
        )
    }
}