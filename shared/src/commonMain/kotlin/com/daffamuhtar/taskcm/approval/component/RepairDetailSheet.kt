package com.daffamuhtar.taskcm.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daffamuhtar.taskcm.approval.ApprovalViewModel
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.contacts.domain.Contact
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.contacts.presentation.components.ContactPhoto
import com.daffamuhtar.taskcm.core.presentation.BottomSheetFromWish
import com.daffamuhtar.taskcm.theme.color_blue
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
    selectedRepair: RepairOrderModel?,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {

    BottomSheetFromWish(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()

    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {

            Surface {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart
                    ) {


                        IconButton(
                            onClick = {
                                onEvent(RepairListEvent.DismissContact)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close"
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
                            selectedRepair?.orderId?.let {

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
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(Modifier.height(60.dp))


                                        KamelImage(
                                            asyncPainterResource(it!!.companyPhoto),
                                            contentDescription = null,
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                            modifier = androidx.compose.ui.Modifier.fillMaxWidth()
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
                                            label = "Bengkel",
                                            value = "${it.repairDistrictName}"
                                        )


                                    }



                                    Text(
                                        text = "Daftar Laporam Masalah",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(Modifier.height(16.dp))

                                    repeat(it.problemDetail.size) { position ->
                                        RepairDetailInfoProblemItem(
                                            problem = it.problemDetail[position].problemDetail

                                        )
                                    }
                                }

                                state.repairDetailAfterCheckItems?.let {

                                    Text(
                                        text = "Hasil Pemeriksaan",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(Modifier.height(16.dp))

                                    Column {
                                        repeat(it.size) { position ->
                                            RepairDetailAfterCheckItem(
                                                repairDetailAfterCheckItem = it[position]
                                            )
                                        }
                                    }


                                }

                                state.repairDetailPartListItems?.let {
                                    println("CloudMessage - Partlist donee")

                                    Text(
                                        text = "Daftar Part",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(Modifier.height(16.dp))


                                    ViewRepairDetailPartList(it)
                                }

                                state.repairDetailPartTotalPrice?.let {

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

                                state.repairDetailWorkshopOfferNote?.let {

                                    Text(
                                        text = "Catatan Penawaran dari Bengkel",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(Modifier.height(16.dp))

                                    Text(
                                        text = "Catatan : $it",
                                    )

                                    Spacer(Modifier.height(16.dp))

                                }


                                Text(
                                    text = "Catatan Penawaran dari Bengkel",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(Modifier.height(16.dp))

                                ApprovalNoteTextField(
                                    value = "alala",
                                    placeholder = "alala",
                                    error = "Kosong",
                                    onValueChanged = {
                                        onEvent(RepairListEvent.OnLastNameChanged(it))
                                    },
                                    modifier = Modifier,

                                    )

                                Button(
                                    onClick = {
                                        onEvent(RepairListEvent.PostReject(
                                            offerId = selectedRepair.offerId,
                                            orderId = selectedRepair.orderId))
                                    }
                                ) {
                                    Text(text = "Tolak Penawaran")
                                }


                                Button(
                                    onClick = {
                                        onEvent(RepairListEvent.PostApproveRepairOrder(
                                            offerId = selectedRepair.offerId,
                                            orderId = selectedRepair.orderId))
                                    }
                                ) {
                                    Text(text = "Approve Penawaran")
                                }

                                state.rejectRepairOrderResponseResult?.let {

                                    if(it.status) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Sukses Reject")
                                        }
                                    }else{
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Gagal Reject")
                                        }
                                    }

                                }

                                state.approveRepairOrderResponseResult?.let {

                                    if(it.status) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Sukses Approve")
                                        }
                                    }else{
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Gagal Approve")
                                        }
                                    }

                                }

                                Spacer(Modifier.height(16.dp))


                            }

                            ////
                        }
                    }

                }

            }

        }

    }
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

        Spacer(Modifier.width(16.dp))

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

        Spacer(Modifier.width(16.dp))

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

            Icon(
                imageVector = Icons.Rounded.ArrowRight,
                contentDescription = "check"
            )

            Text(
                text = repairDetailAfterCheckItem.noteCheckFromMechanic,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold,
                color = color_blue
            )


        }

        LazyRow {
            items(repairDetailAfterCheckItem.checkPhoto) {
                KamelImage(
                    asyncPainterResource(it.checkPhoto),
                    contentDescription = null,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
    ) {


        Divider(color = Color.LightGray, thickness = 1.dp)

        ViewRepairDetailPartListItem(
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

        repeat(repairDetailPartListItems.size) { position ->
            ViewRepairDetailPartListItem(
                repairDetailPartListItems[position]
            )
        }

    }

}

@Composable
fun ViewRepairDetailPartListItem(
    repairDetailPartListItem: RepairDetailPartListItem,
) {

    Column {
        Row {

            Text(
                text = repairDetailPartListItem.partSKU,
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.SemiBold,
                color = color_blue
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.partCategory ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.itemBrand ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.partName ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.vehicleBrand ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.vehicleType ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.genuineOrNon ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.unit ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.partPrice ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.partQuantity ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Text(
                text = repairDetailPartListItem.partTotalPrice ?: "-",
                modifier = Modifier.width(100.dp).padding(10.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

        }

        Divider(color = Color.LightGray, thickness = 1.dp)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ApprovalNoteTextField(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
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

fun lalala() {

}

