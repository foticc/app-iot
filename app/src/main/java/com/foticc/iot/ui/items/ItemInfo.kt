package com.foticc.iot.ui.items

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foticc.iot.R

@Preview(name = "static")
@Composable
fun ItemInfo(modifier: Modifier = Modifier) {
    val fontScale = LocalConfiguration.current.fontScale

    val scaleIndependentFontSize =
        scaleIndependentFontSize(sizeInDp = 24.dp, scaleFactor = fontScale)
    val t = 0xf144;
    BasicText(
        text = t.toChar().toString(),
        style = TextStyle(
            color = Color.Black,
            fontSize = scaleIndependentFontSize,
            fontFamily = FontFamily(Font(resId = R.font.fa_regular_400))
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun DynamicsIcon() {
    var anim by remember {
        mutableStateOf(false)
    }
    ItemInfo(modifier =
    Modifier
        .padding(2.dp)
        .clickable {
            anim = !anim
        }
        .graphicsLayer(
            rotationZ = animateFloatAsState(if (anim) 0f else 720f, tween(2000)).value,
        )
    )
}

private fun scaleIndependentFontSize(sizeInDp: Dp, scaleFactor: Float): TextUnit {
    val materialIconOffset = 3.dp
    return ((sizeInDp - materialIconOffset).value  / scaleFactor).sp
}

@Preview
@Composable
fun DemoSwitch() {

}