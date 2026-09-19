package com.iscoding.designsystem.componenets.actions_buttons_home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iscoding.designsystem.componenets.CircleInBack
import com.iscoding.designsystem.theme.WalletAppTheme

@Composable
fun ActionsButtons(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = 15.dp,
    iconBackgroundSize: Dp = 30.dp,
    iconBackGroundColor: Color = Color.Transparent,
    circleBackGroundColor: Color = Color.Transparent,
    buttonBackGroundColor: Color = Color.Transparent,
    text: String,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = WalletAppTheme.shapes.small,
        color = buttonBackGroundColor
    ) {
        Row(
            modifier = Modifier.sizeIn(maxWidth = 150.dp, minHeight = 50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleInBack(
                modifier = Modifier.size(iconBackgroundSize),
                color = circleBackGroundColor
            ) {
                Icon(modifier = Modifier.size(iconSize), imageVector = icon, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(WalletAppTheme.spacing.small))

            Text(text = text, style = WalletAppTheme.typography.button)
        }

    }

}


@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun ActionsButtonsPreview() {
    WalletAppTheme {
        ActionsButtons(
            icon = WalletAppTheme.icons.RightArrowSend,
            text = "Send Money",
            circleBackGroundColor = WalletAppTheme.colors.primaryContainer,
            buttonBackGroundColor = WalletAppTheme.colors.primaryDark   ,
            onClick = {}
        )
    }
}



