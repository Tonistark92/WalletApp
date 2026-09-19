package com.iscoding.designsystem.componenets.bottom_nav

sealed interface WalletDestination {

    data object Home : WalletDestination

    data object Transfer : WalletDestination

    data object TopUp : WalletDestination

    data object Activity : WalletDestination

    data object Cards : WalletDestination
}