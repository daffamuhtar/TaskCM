package com.daffamuhtar.taskcm.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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