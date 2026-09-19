package com.iscoding.designsystem.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.iscoding.designsystem.R

object WalletAppIcons {

    // Wallet & Cards
    val Bill: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.bill_ic)

    val BlackCard: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.black_card_ic)

    val BlueWallet: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.blue_wallet_ic)

    val Card: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.card_ic)

    val GreyCard: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.grey_card_ic)

    // Security & Authentication
    val Eye: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.eye_ic)

    val Finger: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.finger_ic)

    val Freeze: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.freeze_ic)

    val Pin: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.pin_ic)

    val SecurityShield: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.sec_shield_ic)

    val SecurityShieldRightBlue: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.sec_shield_right_blue_ic)

    val SecurityShieldRight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.sec_shield_right_ic)

    val Verified: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.verfied_ic)

    // Navigation
    val NavCard: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nav_card_ic)

    val NavSend: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nav_send_ic)

    val NavTopUp: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nav_topup_ic)

    val NavWallet: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nav_wallet_ic)

    // NFC
    val NFC: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nfc_ic)

    val NFCSmallWave: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nfc_small_wave_ic)

    // Actions
    val NAVActivity: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.nav_activity_ic)

    val Plus: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.plus_ic)

    val QRCode: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.qrcode_ic)

    val Request: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.request_ic)

    // Arrows
    val RightArrow: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.right_arrow_ic)

    val RightArrowSend: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.right_arrow_send_ic)

    val UpBlue: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.up_blue_ic)

    // Settings
    val Settings: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.settings_ic)
}
internal val LocalWalletAppIcons = staticCompositionLocalOf { WalletAppIcons }