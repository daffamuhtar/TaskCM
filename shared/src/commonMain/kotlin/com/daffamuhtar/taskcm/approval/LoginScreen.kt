package com.daffamuhtar.taskcm.approval

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AssignmentLate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daffamuhtar.taskcm.approval.component.ApprovalNoteTextField
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.core.presentation.BottomSheetFromWish
import com.daffamuhtar.taskcm.theme.color_red
import com.daffamuhtar.taskcm.theme.color_white
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(
    viewModel: ApprovalViewModel,
    state: RepairListState,
    isOpen: Boolean,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    BottomSheetFromWish(
        visible = isOpen,
        enterTransition = slideInVertically(
            animationSpec = tween(durationMillis = 300),
            initialOffsetY = { it }
        ),
        exitTransition = slideOutVertically(
            animationSpec = tween(durationMillis = 300),
            targetOffsetY = { it }
        ),
        modifier = modifier.fillMaxWidth()

    ) {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){

            Column(
                Modifier.fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(

                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    )
                ) {

                    Column {
                        AnimatedVisibility(!(state.isLoadingPostLogin)) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.AssignmentLate,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(10.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
//
//                                Image(
//                                    painter = painterResource(Res.drawable.img_fleetify_full),
//                                    contentDescription = null,
//                                    modifier = Modifier.requiredSize(100.dp)
//                                )

                                Spacer(Modifier.height(15.dp))
                                Text(
                                    text = "Silahkan Login",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                                    fontWeight = FontWeight.SemiBold
                                )


                                Spacer(Modifier.height(15.dp))

                                var username by rememberSaveable {
                                    mutableStateOf("")
                                }

                                var password by rememberSaveable {
                                    mutableStateOf("")
                                }

                                Text(
                                    text = "Username",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(Modifier.height(15.dp))

                                ApprovalNoteTextField(
                                    value = username,
                                    placeholder = "Masukkan Username",
                                    error = state.rejectionNoteError,
                                    onValueChanged = {
                                        username = it
                                    },
                                    modifier = Modifier,

                                    )
                                Spacer(Modifier.height(15.dp))

                                Text(
                                    text = "Password",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.height(15.dp))

                                ApprovalNoteTextField(
                                    value = password,
                                    placeholder = "Masukkan Password",
                                    error = state.rejectionNoteError,
                                    onValueChanged = {
                                        password = it
                                    },
                                    modifier = Modifier,

                                    )

                                Spacer(Modifier.height(15.dp))

                                Button(
                                    modifier = Modifier.fillMaxWidth()
                                        .height(50.dp),
                                    onClick = {

                                        if (username.isNotEmpty() && password.isNotEmpty()) {

                                            onEvent(
                                                RepairListEvent.PostLogin(
                                                    username = username,
                                                    password = password
                                                )
                                            )
                                        }

                                    }
                                ) {
                                    Text(text = "Login")
                                }

                            }
                        }

                        AnimatedVisibility(state.isLoadingPostLogin) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                CircularProgressIndicator(
                                )

                                Spacer(Modifier.height(15.dp))

                                Text(
                                    text = "Harap Menunggu",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 17.sp),
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(Modifier.height(10.dp))

                                Text(
                                    text = "Memeriksa Username & Password",
                                    textAlign = TextAlign.Center,
                                )

                                Spacer(Modifier.height(15.dp))


                            }
                        }
                    }


                }
            }
        }

    }
}