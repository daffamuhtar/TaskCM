package com.daffamuhtar.taskcm.approval.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.AssignmentTurnedIn
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daffamuhtar.taskcm.approval.ApprovalViewModel
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.contacts.domain.Contact
import com.daffamuhtar.taskcm.contacts.presentation.components.ContactPhoto
import com.daffamuhtar.taskcm.core.presentation.BottomSheetFromWish
import com.daffamuhtar.taskcm.platform.Res
import com.daffamuhtar.taskcm.theme.color_black
import com.daffamuhtar.taskcm.theme.color_blue
import com.daffamuhtar.taskcm.theme.color_grey
import com.daffamuhtar.taskcm.theme.color_grey0
import com.daffamuhtar.taskcm.theme.color_grey1
import com.daffamuhtar.taskcm.theme.color_red
import com.daffamuhtar.taskcm.theme.color_white
import com.daffamuhtar.taskcm.theme.color_yellow
import com.daffamuhtar.taskcm.theme.color_yellowsoft
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun ApprovalConfirmationBottomSheet(
    viewModel: ApprovalViewModel,
    state: RepairListState,
    isOpen: Boolean,
    selectedRepair: RepairOrderModel?,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
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
                    AnimatedVisibility(!(state.isLoadingPostApproveRepairOrder)) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AssignmentTurnedIn,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(color_yellow)
                                    .padding(10.dp),
                                tint = color_black,
                            )
                            Spacer(Modifier.height(15.dp))

                            Text(
                                text = "Approve Surat Penawaran?",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 17.sp),
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = "Perbaikan akan diteruskan ke bengkel untuk ditugaskan pada mekanik",
                                textAlign = TextAlign.Center,
                            )

                            Spacer(Modifier.height(15.dp))

                            Spacer(Modifier.height(15.dp))

                            Button(
                                modifier = Modifier.fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = color_yellow),
                                onClick = {
                                    selectedRepair?.let {
                                        onEvent(
                                            RepairListEvent.PostApproveRepairOrder(
                                                offerId = selectedRepair.offerId,
                                                orderId = selectedRepair.orderId
                                            )
                                        )
                                    }

                                }
                            ) {
                                Text(text = "Approve Penawaran")
                            }

                            Spacer(Modifier.height(10.dp))

                            Button(
                                colors = ButtonDefaults.buttonColors(containerColor = color_grey1),
                                modifier = Modifier.fillMaxWidth()
                                    .height(50.dp),
                                onClick = {
                                    onEvent(
                                        RepairListEvent.DismissApproveRepairOrder
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


                    AnimatedVisibility(state.isLoadingPostApproveRepairOrder) {
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
                                text = "Mengirim approval",
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



