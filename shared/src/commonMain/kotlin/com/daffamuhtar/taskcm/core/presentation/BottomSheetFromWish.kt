package com.daffamuhtar.taskcm.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetFromWish(
    visible: Boolean,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
//        enter = slideInVertically(
//            animationSpec = tween(durationMillis = 300),
//            initialOffsetY = { it }
//        ),
//        exit = slideOutVertically(
//            animationSpec = tween(durationMillis = 300),
//            targetOffsetY = { it }
//        ),

//        enter = fadeIn(animationSpec = tween(2000)),
//        exit = fadeOut(animationSpec = tween(2000))

        enter = enterTransition,
        exit = exitTransition

    ) {
        Column(
            modifier = modifier
//                .clip(
//                    RoundedCornerShape(
//                        topStart = 30.dp,
//                        topEnd = 30.dp,
//                    )
//                )
        ) {
            content()
        }
    }
}