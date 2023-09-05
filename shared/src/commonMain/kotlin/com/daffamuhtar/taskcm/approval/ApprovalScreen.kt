package com.daffamuhtar.taskcm.approval

import com.daffamuhtar.taskcm.approval.component.RepairDetailSheet
import com.daffamuhtar.taskcm.approval.component.RepairListItem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.theme.color_black
import com.daffamuhtar.taskcm.theme.color_red
import com.daffamuhtar.taskcm.theme.color_yellow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

object ReplyRoute {
    const val INBOX = "Adhoc Approval Order Baru asdasdasdasdasd asdasdas "
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApprovalScreen(
    state: RepairListState,
    onEvent: (RepairListEvent) -> Unit,
    fcmToken: String?,

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scopeScaffold = rememberCoroutineScope()

    val approvalViewModel = getViewModel(Unit, viewModelFactory { ApprovalViewModel() })
//    val uiState by approvalViewModel.uiState.collectAsState()

    val menuAdhoc = listOf(
        NavigationItem(
            title = "Surat Penawaran",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "Penambahan Part",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            badgeCount = 45
        ),
        NavigationItem(
            title = "Status Berjalan",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )

    val menuNonPeriod = listOf(
        NavigationItem(
            title = "Approval PNB",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "Penambahan Part",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            badgeCount = 45
        ),
        NavigationItem(
            title = "Status Berjalan",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var selectedItemTitle by rememberSaveable {
        mutableStateOf("Surat Penawaran")
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet (
//                drawerContainerColor = color_yellow
            ){

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Perbaikan Adhoc",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 15.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                menuAdhoc.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = color_black,
                            selectedIconColor= color_yellow,
                            selectedTextColor=color_yellow,
                        ),
                        label = {
                            Text(
                                text = item.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            selectedItemTitle = item.title
                            when (index) {
                                0 -> {
                                    onEvent(RepairListEvent.OnLoadingRepairOrderList(index))
                                }

                                1 -> {
                                    onEvent(RepairListEvent.OnLoadingRepairOrderList(index))
                                }

                                2 -> {
                                    onEvent(RepairListEvent.OnLoadingRepairOrderList(index))
                                }

                            }

                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(color_red)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = item.badgeCount.toString(),
                                        color = Color.White
                                    )

                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Divider(color = Color.LightGray, thickness = 1.dp)

//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "Perbaikan Non Berkala",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
//                    modifier = Modifier.padding(horizontal = 15.dp),
//
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//                menuNonPeriod.forEachIndexed { index, item ->
//                    NavigationDrawerItem(
//                        label = {
//                            Text(
//                                text = item.title,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                        },
//                        selected = index == selectedItemIndex,
//                        onClick = {
//                            selectedItemIndex = index
//                            selectedItemTitle = item.title
//                            when (index) {
//                                0 -> {
//                                    onEvent(RepairListEvent.OnLoadingRepairOrderList)
//                                }
//
//                                1 -> {
//                                    onEvent(RepairListEvent.OnLoadingRepairOrderList)
//                                }
//
//                                2 -> {
//                                    onEvent(RepairListEvent.OnLoadingRepairOrderList)
//                                }
//
//                            }
//
//                            scope.launch {
//                                drawerState.close()
//                            }
//                        },
//                        icon = {
//                            Icon(
//                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
//                                contentDescription = item.title
//                            )
//                        },
//                        badge = {
//                            item.badgeCount?.let {
//
//                                Box(
//                                    modifier = Modifier
//                                        .clip(CircleShape)
//                                        .background(MaterialTheme.colorScheme.onPrimary)
//                                        .padding(8.dp)
//                                ) {
//                                    Text(text = item.badgeCount.toString())
//
//                                }
//                            }
//                        },
//                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                }
            }

        },
        drawerState = drawerState
    ) {


        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }

        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = color_yellow),
                    title = {
                        Text(
                            fontWeight = FontWeight.SemiBold,
                            text = selectedItemTitle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )

                Scaffold(
                    Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {

                            },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PersonAdd,
                                contentDescription = "Add contact"
                            )
                        }
                    }
                ) {


                    if (state.isLoadingRepairOrderList) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Memuat Perbaikan")
                        }
                    }


                    if (state.repairOrderModels != null) {
                        AnimatedVisibility(state.repairOrderModels != null) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = 15.dp,
                                        end = 15.dp,
                                    ),
                                contentPadding = PaddingValues(vertical = 15.dp),
                                verticalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                items(state.repairOrderModels) {
                                    RepairListItem(
                                        repairOrderModel = it,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        onClick = { onEvent(RepairListEvent.SelectRepairItem(it)) }

                                    )
                                }
                            }
                        }
                    } else {
                        AnimatedVisibility(state.repairOrderModels == null) {
                            Text("Memuat")
                        }
                    }
                }
            }
        }
    }

    RepairDetailSheet(
        viewModel = approvalViewModel,
        state = state,
        onEvent = onEvent,
        isOpen = state.isSelectedContactSheetOpen,
        selectedRepairOrder = state.selectedRepairOrderModel,
        snackbarHostState = snackbarHostState,
        scope = scope,
    )

}


