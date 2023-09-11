package com.daffamuhtar.taskcm.approval.component

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.daffamuhtar.taskcm.theme.color_black
import com.daffamuhtar.taskcm.theme.color_blue
import com.daffamuhtar.taskcm.theme.color_grey
import com.daffamuhtar.taskcm.theme.color_grey0
import com.daffamuhtar.taskcm.theme.color_red
import com.daffamuhtar.taskcm.theme.color_white
import com.daffamuhtar.taskcm.theme.color_yellow
import com.daffamuhtar.taskcm.theme.color_yellowsoft
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairDetailSheet(
    viewModel: ApprovalViewModel,
    state: RepairListState,
    isOpen: Boolean,
    selectedRepairOrder: RepairOrderModel?,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
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
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = false,
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Sukses Gaboleh")
                    }
                }
            )

    ) {
        Scaffold(
        ) {

            Surface {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                            .background(color_yellow),
                        contentAlignment = Alignment.TopStart
                    ) {


                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier.padding(end = 20.dp),
                                onClick = {
                                    onEvent(RepairListEvent.DismissContact)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Close"
                                )
                            }

                            Text(
                                text = "Detail Order",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold
                            )


                        }


                    }

                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .weight(1f, false)
                                .fillMaxWidth()

                        ) {
                            selectedRepairOrder?.orderId?.let {

                                if (state.repairDetailInfo == null) {
                                    onEvent(RepairListEvent.OnLoadingRepairDetailInfo)
                                }

                                if (state.repairDetailAfterCheckItems == null) {
                                    onEvent(RepairListEvent.OnLoadingRepairDetailAfterCheck)
                                }

                                if (state.repairDetailPartListItems == null) {
                                    onEvent(RepairListEvent.OnLoadingRepairDetailPartList)
                                }

                                if (state.repairDetailPartTotalPrice == null) {
                                    onEvent(RepairListEvent.OnLoadingRepairDetailPartTotalPrice)
                                }

                                if (state.repairDetailWorkshopOfferNote == null) {
                                    onEvent(RepairListEvent.OnLoadingRepairDetailWorkshopOfferNote)
                                }

                                state.repairDetailInfo?.let {

                                    Card(
                                        shape = RoundedCornerShape(0.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 5.dp
                                        ),
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            KamelImage(
                                                asyncPainterResource(it.companyPhoto),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxWidth()
                                                    .aspectRatio(1.0f)
                                            )

                                            RepairDetailInfoItem(
                                                label = "Kendaraan",
                                                value = "${it.vehicleName}\n${it.vehicleLicenseNumber}"
                                            )

                                            RepairDetailInfoItem(
                                                label = "Distrik",
                                                value = it.vehicleDistrict
                                            )

                                            RepairDetailInfoItem(
                                                label = "KM Pengerjaan",
                                                value = "${it.odometerAt} KM"
                                            )

                                            RepairDetailInfoItem(
                                                label = "Tanggal Pengerjaan",
                                                value = "${it.scheduledDate}"
                                            )

                                            RepairDetailInfoItem(
                                                label = "Bengkel",
                                                value = "${it.workshopName}"
                                            )

                                            RepairDetailInfoItem(
                                                label = "Distrik Pengerjaan",
                                                value = "${it.repairDistrictName}"
                                            )


                                        }

                                    }

                                    Spacer(Modifier.height(15.dp))

                                    Card(
                                        shape = RoundedCornerShape(0.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 5.dp
                                        ),
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp),
                                        ) {
                                            Text(
                                                text = "Daftar Laporan Masalah",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(Modifier.height(15.dp))

                                            repeat(it.problemDetail.size) { position ->
                                                RepairDetailInfoProblemItem(
                                                    problem = it.problemDetail[position].problemDetail

                                                )
                                            }
                                        }

                                    }

                                }


                                state.repairDetailAfterCheckItems?.let {
                                    Spacer(Modifier.height(15.dp))

                                    Card(
                                        shape = RoundedCornerShape(0.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 5.dp
                                        ),
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp),
                                        ) {
                                            Text(
                                                text = "Hasil Pemeriksaan",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(Modifier.height(15.dp))

                                            Column {
                                                repeat(it.size) { position ->
                                                    RepairDetailAfterCheckItem(
                                                        repairDetailAfterCheckItem = it[position]
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                state.repairDetailPartListItems?.let {
                                    Spacer(Modifier.height(15.dp))
                                    Card(
                                        shape = RoundedCornerShape(0.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 5.dp
                                        ),
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp),
                                        ) {
                                            Text(
                                                text = "Daftar Part",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(Modifier.height(15.dp))

                                            ViewRepairDetailPartList(it)

                                            state.repairDetailPartTotalPrice?.let {

                                                Spacer(Modifier.height(15.dp))

                                                RepairDetailInfoItem(
                                                    label = "Sub Total",
                                                    value = "Rp. ${it.subTotal}",
                                                    valueColor = Color.Black
                                                )

                                                RepairDetailInfoItem(
                                                    label = "Diskon",
                                                    value = "- Rp. ${it.discount}"

                                                )
                                                RepairDetailInfoItem(
                                                    label = "PPN",
                                                    value = "Rp. ${it.tax}",
                                                    valueColor = Color.Black

                                                )

                                                RepairDetailInfoItem(
                                                    label = "Total",
                                                    value = "Rp. ${it.subTotalAfterDiscount}",
                                                    valueColor = color_blue

                                                )
                                            }

                                        }


                                    }

                                }


                                state.repairDetailWorkshopOfferNote?.let {

                                    Spacer(Modifier.height(15.dp))

                                    Card(
                                        shape = RoundedCornerShape(0.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 5.dp
                                        ),
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp),
                                        ) {
                                            Text(
                                                text = "Catatan Penawaran dari Bengkel",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.SemiBold
                                            )

                                            Spacer(Modifier.height(15.dp))

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(color_grey0)

                                            ) {
                                                Column(
                                                    modifier = Modifier.padding(15.dp)
                                                ) {
                                                    Row {
                                                        Icon(
                                                            modifier = Modifier
                                                                .padding(end = 10.dp),
                                                            imageVector = Icons.Rounded.Assignment,
                                                            contentDescription = "check",
                                                            tint = color_grey
                                                        )

                                                        Text(
                                                            text = "Catatan Bengkel",
                                                            fontWeight = FontWeight.SemiBold,
                                                            color = color_grey
                                                        )
                                                    }

                                                    Text(
                                                        modifier = Modifier
                                                            .padding(start = 34.dp),
                                                        text = it,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = color_black
                                                    )
                                                }

                                            }


                                        }
                                    }

                                }

                                Spacer(Modifier.height(15.dp))

                                Card(
                                    shape = RoundedCornerShape(0.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 5.dp
                                    ),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp),
                                    ) {

                                        Spacer(Modifier.height(15.dp))

                                        Button(
                                            modifier = Modifier.fillMaxWidth()
                                                .height(50.dp),
                                            onClick = {
                                                onEvent(
                                                    RepairListEvent.DoApproveRepairOrder(
                                                        selectedRepairOrder
                                                    )
                                                )
                                            }
                                        ) {
                                            Text(text = "Approve Penawaran")
                                        }

                                        Spacer(Modifier.height(15.dp))

                                        Button(
                                            modifier = Modifier.fillMaxWidth()
                                                .height(50.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = color_red,
                                                contentColor = color_white
                                            ),
                                            onClick = {
                                                onEvent(
                                                    RepairListEvent.DoRejectRepairOrder(
                                                        selectedRepairOrder
                                                    )
                                                )
                                            }
                                        ) {
                                            Text(
                                                text = "Tolak Penawaran",
                                            )
                                        }


                                    }
                                }


                                state.rejectRepairOrderResponseResult?.let {

                                    if (it.status) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Sukses Reject", withDismissAction = true)
                                        }
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Gagal Reject", withDismissAction = true)
                                        }
                                    }

                                }

                                state.approveRepairOrderResponseResult?.let {

                                    if (it.status) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Sukses Approve", withDismissAction = true)
                                        }
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Gagal Approve", withDismissAction = true)
                                        }
                                    }

                                }

                            }

                        }
                    }

                }

            }

        }

    }

    ApprovalConfirmationBottomSheet(
        viewModel = viewModel,
        state = state,
        onEvent = onEvent,
        isOpen = state.isApproveConfirmationSheetOpen,
        selectedRepair = state.selectedRepairOrderModel,
        snackbarHostState = snackbarHostState,
        scope = scope,
    )

    RejectConfirmationBottomSheet(
        viewModel = viewModel,
        state = state,
        onEvent = onEvent,
        isOpen = state.isRejectConfirmationSheetOpen,
        selectedRepair = state.selectedRepairOrderModel,
        snackbarHostState = snackbarHostState,
        scope = scope,
    )
}

data class RepairDetailInfoItem(
    val label: String,
    val value: ImageVector,
)

@Composable
fun EditRow(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        FilledTonalIconButton(
            onClick = onEditClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit contact"
            )
        }

        FilledTonalIconButton(
            onClick = onDeleteClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete contact"
            )
        }
    }
}

@Composable
private fun ContactInfoSection(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Spacer(Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp
            )
            Text(
                text = value,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactPhoto(
            contact = contact,
            modifier = Modifier.size(50.dp)
        )

        Spacer(Modifier.width(15.dp))

        Text(
            text = "${contact.firstName} ${contact.lastName}",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RepairDetailInfoItem(
    label: String,
    value: String,
    labelColor: Color? = Color.Black,
    valueColor: Color? = Color.Red,
) {
    Column {
        Row {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold,
                color = labelColor ?: Color.Black
            )

            Text(
                text = value,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                color = valueColor ?: Color.Red
            )
        }

        Spacer(Modifier.height(10.dp))
    }

}

@Composable
fun RepairDetailInfoProblemItem(
    problem: String,
) {
    Column {

        Row {

            Icon(
                imageVector = Icons.Rounded.ArrowRight,
                contentDescription = problem
            )

            Text(
                text = problem,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold,
                color = color_blue
            )

        }

        Spacer(Modifier.height(10.dp))
    }

}

@Composable
fun RepairDetailAfterCheckItem(
    repairDetailAfterCheckItem: RepairDetailAfterCheckItem,
) {
    Column {

        Row {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color_grey0)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Row {
                        Icon(
                            modifier = Modifier
                                .padding(end = 10.dp),
                            imageVector = Icons.Rounded.Assignment,
                            contentDescription = "check",
                            tint = color_grey
                        )

                        Text(
                            text = "Catatan Mekanik",
                            fontWeight = FontWeight.SemiBold,
                            color = color_grey
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(start = 34.dp),
                        text = repairDetailAfterCheckItem.noteCheckFromMechanic,
                        fontWeight = FontWeight.SemiBold,
                        color = color_black
                    )
                }

            }


        }

        Spacer(Modifier.height(15.dp))

        LazyRow {
            items(repairDetailAfterCheckItem.checkPhoto) {
                KamelImage(
                    asyncPainterResource(it.checkPhoto),
                    contentDescription = null,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .aspectRatio(1.0f)
                )
            }
        }

        Spacer(Modifier.height(10.dp))
    }

}

@Composable
fun ViewRepairDetailPartList(
    repairDetailPartListItems: List<RepairDetailPartListItem>,
) {
    val scrollState = rememberScrollState()


    Box(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .horizontalScroll(rememberScrollState())
            .border(
                width = 1.dp,
                color = color_grey,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        var width by remember { mutableStateOf(0) }
        val density = LocalDensity.current
        val widthDp = remember(width, density) { with(density) { width.toDp() } }

        Column(
            modifier = Modifier.onSizeChanged {
                width = it.width
            }
        ) {


            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(widthDp)
            )

            ViewRepairDetailPartListItem(
                fontWeight = FontWeight.SemiBold,
                backgroundColor = color_yellowsoft,
                RepairDetailPartListItem(
                    genuineOrNon = "Genuine",
                    itemBrand = "Merek Part",
                    partCategory = "Kategori",
                    partCategoryId = "partCategoryId",
                    partIdFromFleetify = "partIdFromFleetify",
                    partIdFromWorkshop = "partIdFromWorkshop",
                    partName = "Nama Part",
                    partPrice = "Harga Part",
                    partQuantity = "Jumlah Part",
                    partSKU = "SKU",
                    partTotalPrice = "Harga Total",
                    unit = "Satuan",
                    vehicleBrand = "Merek Kendaraan",
                    vehicleType = "Tipe Kendaraan",
                    vehicleTypeId = "vehicleTypeId"
                )
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(widthDp)
            )

            repeat(repairDetailPartListItems.size) { position ->
                ViewRepairDetailPartListItem(
                    fontWeight = FontWeight.Normal,
                    backgroundColor = if ((position % 2) == 0 || position == 0) color_white else color_grey0,

                    repairDetailPartListItems[position]
                )
            }

        }

//        LazyColumn(
//            modifier = Modifier.onSizeChanged {
//                width = it.width
//            }
//        ) {
//
//            item {
//                ViewRepairDetailPartListItem(
//                    RepairDetailPartListItem(
//                        genuineOrNon = "Genuine",
//                        itemBrand = "Merek Part",
//                        partCategory = "Kategori",
//                        partCategoryId = "partCategoryId",
//                        partIdFromFleetify = "partIdFromFleetify",
//                        partIdFromWorkshop = "partIdFromWorkshop",
//                        partName = "Nama Part",
//                        partPrice = "Harga Part",
//                        partQuantity = "Jumlah Part",
//                        partSKU = "SKU",
//                        partTotalPrice = "Harga Total",
//                        unit = "Satuan",
//                        vehicleBrand = "Merek Kendaraan",
//                        vehicleType = "Tipe Kendaraan",
//                        vehicleTypeId = "vehicleTypeId"
//                    )
//                )
//            }
//            items(repairDetailPartListItems.size) {
//                ViewRepairDetailPartListItem(
//                    repairDetailPartListItems[it]
//                )
//                Divider(
//                    thickness = 10.dp,
//                    color = Color.Red,
//                    modifier = Modifier.width(widthDp)
//                )
//            }
//        }
    }


}

@Composable
fun ViewRepairDetailPartListItem(
    fontWeight: FontWeight,
    backgroundColor: Color,
    repairDetailPartListItem: RepairDetailPartListItem,
) {

    Spacer(modifier = Modifier.background(Color.Red).width(10.dp).fillMaxHeight())

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.background(Color.Blue).width(10.dp).fillMaxHeight())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = repairDetailPartListItem.partSKU,
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.SemiBold,
                color = color_blue
            )

            Spacer(modifier = Modifier.background(Color.Red).width(1.dp).fillMaxHeight())

            Box(
                Modifier
                    .width(20.dp)
                    .fillMaxHeight()
                    .background(Color.Red)
            )


            Text(
                text = repairDetailPartListItem.partCategory ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Spacer(modifier = Modifier.background(Color.Red).width(1.dp).fillMaxHeight())
            Box(
                Modifier
                    .width(20.dp)
                    .fillMaxHeight()
                    .background(Color.Red)
            )

            Text(
                text = repairDetailPartListItem.itemBrand ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                color = Color.LightGray, modifier = Modifier
                    .fillMaxHeight()  //fill the max height
                    .width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.partName ?: "-",
                modifier = Modifier.width(170.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.vehicleBrand ?: "-",
                modifier = Modifier.width(120.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.vehicleType ?: "-",
                modifier = Modifier.width(120.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.genuineOrNon ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.unit ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.partPrice ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.partQuantity ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

            Text(
                text = repairDetailPartListItem.partTotalPrice ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = fontWeight,
                color = Color.Black
            )

            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.width(1.dp)
            )

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApprovalNoteTextField(
    value: String?,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = value ?: "" ,
            placeholder = {
                Text(text = placeholder)
            },
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


