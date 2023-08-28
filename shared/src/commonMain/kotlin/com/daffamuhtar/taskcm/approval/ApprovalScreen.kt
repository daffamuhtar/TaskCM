package com.daffamuhtar.taskcm.approval

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApprovalScreen(
    state: RepairListState,
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: ApprovalViewModel,
    onEvent: (RepairListEvent) -> Unit,
) {

}
