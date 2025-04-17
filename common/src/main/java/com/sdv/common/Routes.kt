package com.sdv.common

sealed class Routes(val route: String) {
    data object Main : Routes("mainScreen")
}