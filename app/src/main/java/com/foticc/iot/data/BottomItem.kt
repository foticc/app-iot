package com.foticc.iot.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send


import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomItem(val route:String,val title:String,val icon:ImageVector) {
    object Scanner: BottomItem("scanner","扫一扫", Icons.Default.Send)
    object Home: BottomItem("home","主页",Icons.Default.Home)
    object Collections: BottomItem("collections","集合",Icons.Default.AccountBox)

}