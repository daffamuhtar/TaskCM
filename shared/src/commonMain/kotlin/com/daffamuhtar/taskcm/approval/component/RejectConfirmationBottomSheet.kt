package com.daffamuhtar.taskcm.approval.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
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
import com.daffamuhtar.taskcm.approval.ApprovalViewModel
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.core.presentation.BottomSheetFromWish
import com.daffamuhtar.taskcm.theme.color_black
import com.daffamuhtar.taskcm.theme.color_grey1
import com.daffamuhtar.taskcm.theme.color_red
import com.daffamuhtar.taskcm.theme.color_white
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun RejectConfirmationBottomSheet(
    viewModel: ApprovalViewModel,
    state: RepairListState,
    isOpen: Boolean,
    selectedRepair: RepairOrderModel?,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {

    BottomSheetFromWish(
        visible = isOpen,
        enterTransition = fadeIn(animationSpec = tween(300)),
        exitTransition = fadeOut(animationSpec = tween(300)),
        modifier = modifier.fillMaxWidth()

    ) {


//            Box(modifier = Modifier.fillMaxSize()
//                .background(Color.Black)
//                .alpha(0.5f),
//            )
//        Surface(
//            color = Color.Black.copy(alpha = 0.6f),
//            modifier = Modifier.fillMaxSize()
//        ){
//            //....
//        }
        Column(
            Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
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
                    AnimatedVisibility(!(state.isLoadingPostRejectRepairOrder)) {
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
                                    .background(MaterialTheme.colorScheme.errorContainer)
                                    .padding(10.dp),
                                tint = color_red,
                            )
                            Spacer(Modifier.height(15.dp))

                            Text(
                                text = "Tolak Surat Penawaran?",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 17.sp),
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = "Surat Penawaran akan dikembalikan ke bengkel",
                                textAlign = TextAlign.Center,
                            )

                            Spacer(Modifier.height(15.dp))

                            var rejectionNote by rememberSaveable {
                                mutableStateOf("")
                            }

                            ApprovalNoteTextField(
                                value = rejectionNote,
                                placeholder = "Masukkan catatan penolakkan",
                                error = state.rejectionNoteError,
                                onValueChanged = {
                                    rejectionNote = it
                                },
                                modifier = Modifier,

                                )

                            Spacer(Modifier.height(15.dp))

                            Button(
                                colors = ButtonDefaults.buttonColors(containerColor = color_red, contentColor = color_white),
                                modifier = Modifier.fillMaxWidth()
                                    .height(50.dp),
                                onClick = {
                                    selectedRepair?.let {
                                        rejectionNote?.let {
                                            onEvent(
                                                RepairListEvent.PostReject(
                                                    offerId = selectedRepair.offerId,
                                                    orderId = selectedRepair.orderId,
                                                    rejectionNote = rejectionNote!!,
                                                )
                                            )
                                        }
                                    }

                                }
                            ) {
                                Text(text = "Tolak Penawaran")
                            }

                            Spacer(Modifier.height(10.dp))

                            Button(
                                colors = ButtonDefaults.buttonColors(containerColor = color_grey1),
                                modifier = Modifier.fillMaxWidth()
                                    .height(50.dp),
                                onClick = {
                                    onEvent(
                                        RepairListEvent.DismissRejectRepairOrder
                                    )

                                }
                            ) {
                                Text(
                                    text = "Periksa Kembali",
                                    color = color_black
                                )
                            }


                        }
                    }


                    AnimatedVisibility(state.isLoadingPostRejectRepairOrder) {
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
                                text = "Menolak surat penawaran",
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



