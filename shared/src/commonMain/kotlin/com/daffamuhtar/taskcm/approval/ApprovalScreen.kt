package com.daffamuhtar.taskcm.approval

import com.daffamuhtar.taskcm.approval.component.RepairDetailSheet
import com.daffamuhtar.taskcm.approval.component.RepairListItem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.AssignmentReturn
import androidx.compose.material.icons.rounded.AssignmentTurnedIn
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.daffamuhtar.taskcm.approval.utils.LoginState
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import com.daffamuhtar.taskcm.theme.color_black
import com.daffamuhtar.taskcm.theme.color_yellow
import kotlinx.coroutines.CoroutineScope
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
    loggedUserId: String?,
    userToken: String?,
    approvalViewModel: ApprovalViewModel,
    mainViewModel: MainViewModel?,
    stateMain: LoginState?,
    snackbarHostState: SnackbarHostState,
    scopeScaffold: CoroutineScope,
    count: Int,
) {

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
        mutableStateOf("surat-penawaran-submenu")
    }

    var selectedItemTitle by rememberSaveable {
        mutableStateOf("Surat Penawaran")
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
//                drawerContainerColor = color_yellow
            ) {

                state.loginResponse?.menus?.let {
                    it.forEachIndexed { index, item ->

                        if (item.menu.id == "perbaikan-big-menu" ||
                            item.menu.id == "pnb-big-menu" ||
                            item.menu.id == "ban-big-menu"
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = item.menu.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 15.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            item.submenu.forEachIndexed { index, submenu ->
                                NavigationDrawerItem(
                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = color_black,
                                        selectedIconColor = color_yellow,
                                        selectedTextColor = color_yellow,
                                    ),
                                    label = {
                                        Text(
                                            text = submenu.name,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    selected = item.submenu[index].id == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = item.submenu[index].id
                                        selectedItemTitle = submenu.name
                                        when (index) {
                                            0 -> {
                                                onEvent(
                                                    RepairListEvent.OnLoadingRepairOrderList(
                                                        index
                                                    )
                                                )
                                            }

                                            1 -> {
                                                onEvent(
                                                    RepairListEvent.OnLoadingRepairOrderList(
                                                        index
                                                    )
                                                )
                                            }

                                            2 -> {
                                                onEvent(
                                                    RepairListEvent.OnLoadingRepairOrderList(
                                                        index
                                                    )
                                                )
                                            }

                                        }

                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        val icon: ImageVector
                                        when (index) {
                                            0 -> {
                                                icon = Icons.Rounded.Assignment
                                            }

                                            1 -> {
                                                icon = Icons.Rounded.AssignmentReturn

                                            }

                                            2 -> {
                                                icon = Icons.Rounded.AssignmentTurnedIn
                                            }

                                        }

                                        Icon(
                                            imageVector = when (index) {
                                                0 -> {
                                                    Icons.Rounded.Assignment
                                                }

                                                1 -> {
                                                    Icons.Rounded.AssignmentReturn

                                                }

                                                2 -> {
                                                    Icons.Rounded.AssignmentTurnedIn
                                                }

                                                else -> {
                                                    Icons.Rounded.AssignmentTurnedIn

                                                }
                                            },
                                            contentDescription = submenu.name
                                        )
                                    },
                                    badge = {
//                                    submenu.badgeCount?.let {
//
//                                        Box(
//                                            modifier = Modifier
//                                                .clip(CircleShape)
//                                                .background(color_red)
//                                                .padding(8.dp)
//                                        ) {
//                                            Text(
//                                                text = submenu.badgeCount.toString(),
//                                                color = Color.White
//                                            )
//
//                                        }
//                                    }
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = Color.LightGray, thickness = 1.dp)

                        }

                    }
                } ?: run {
                    Text(
                        text = "Kosong waaa",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

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
                                stateMain?.loginResponseString?.let { it1 ->
                                        approvalViewModel.showSnackbar(
                                            it1
                                        )
                                } ?: run{
                                }
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
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Memuat Perbaikan")
//                        }
                    }

                    if (state.isSuccessLogin){
                        scope.launch {
                            snackbarHostState.showSnackbar("Sukses Login", withDismissAction = true)
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

    LoginScreen(
        viewModel = approvalViewModel,
        state = state,
        onEvent = onEvent,
        isOpen = (state.loginResponse == null),
        snackbarHostState = snackbarHostState,
        scope = scope,
    )

}


