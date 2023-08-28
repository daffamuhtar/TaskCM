package com.daffamuhtar.taskcm.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.HomeRepairService
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.Payment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.daffamuhtar.taskcm.approval.data.RepairItem
import com.daffamuhtar.taskcm.theme.color_blue
import com.daffamuhtar.taskcm.theme.color_bluesoft
import com.daffamuhtar.taskcm.theme.color_adhoc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairListItem(
    repairItem: RepairItem,
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color_adhoc)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = "adhoc",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Perbaikan Adhoc",
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = repairItem.orderId,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocalShipping,
                        contentDescription = "vehicle",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(10.dp))


                    Text(
                        text = "${repairItem.vehicleName}\n${repairItem.vehicleLicenseNumber}",
                        modifier = Modifier.weight(1f),
//                    style = MaterialTheme.typography.bodySmall

                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                            .padding(5.dp)
                            .width(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = repairItem.vehicleDistrict,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.HomeRepairService,
                        contentDescription = "Pay",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = repairItem.workshopName,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Payment,
                        contentDescription = "Pay",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = repairItem.totalAfterTax,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color_bluesoft)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = "Stage",
                        modifier = Modifier.size(20.dp),
                        tint = color_blue
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Menunggu Approval",
                        modifier = Modifier.weight(1f),
                        color = color_blue,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold
                    )

                }

            }
        }
    }


//        KamelImage(
//            asyncPainterResource("https://storage.googleapis.com/fleetifyid_images_staging/VHC-BLOG-1/issues/adhoc/2023/07/03/PRB-03072023-161314-VHC-BLOG-1-UM-BLOG-9999-1.jpg"),
//            "${image.problemStage} by ${image.problemId}",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
//        )

}