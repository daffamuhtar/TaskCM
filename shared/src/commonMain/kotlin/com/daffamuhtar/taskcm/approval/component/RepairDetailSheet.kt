package com.daffamuhtar.taskcm.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daffamuhtar.taskcm.approval.ApprovalViewModel
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairItem
import com.daffamuhtar.taskcm.contacts.domain.Contact
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.contacts.presentation.components.ContactPhoto
import com.daffamuhtar.taskcm.core.presentation.BottomSheetFromWish
import com.daffamuhtar.taskcm.theme.color_blue
import com.daffamuhtar.taskcm.theme.color_red
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch

@Composable
fun RepairDetailSheet(
    viewModel: ApprovalViewModel,
    state: RepairListState,
    isOpen: Boolean,
    selectedRepair: RepairItem?,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    BottomSheetFromWish(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()

    ) {
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                ),
            contentPadding = PaddingValues(vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            selectedRepair?.orderId?.let {

                if (!state.isLoadingGetRepairDetailInfoAdhoc) {
                    onEvent(RepairListEvent.OnLoadingRepairDetailInfo)
                }

                if (!state.isLoadingGetRepairDetailAfterCheck) {
                    onEvent(RepairListEvent.OnLoadingRepairDetailAfterCheck)
                }


                state.repairDetailInfo?.let {

                    item {
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




                    }

                    item {
                        Text(
                            text = "Daftar Laporam Masalah",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(16.dp))

                    }

                    items(it.problemDetail) {
                        RepairDetailInfoProblemItem(
                            problem = it.problemDetail

                        )
                    }
                }

                item {
                    Text(
                        text = "Hasil Pemeriksaan",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(16.dp))

                }

                state.repairDetailAfterCheckItems?.let {
                    items(it) {
                        RepairDetailAfterCheckItem(
                            repairDetailAfterCheckItem = it
                        )
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
    value: String
) {
    Column {
        Row {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = value,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                color = color_red
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
            items(repairDetailAfterCheckItem.checkPhoto){
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