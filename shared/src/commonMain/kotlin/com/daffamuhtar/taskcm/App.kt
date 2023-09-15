package com.daffamuhtar.taskcm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.daffamuhtar.taskcm.approval.ApprovalScreen
import com.daffamuhtar.taskcm.approval.ApprovalViewModel
import com.daffamuhtar.taskcm.approval.MainViewModel
import com.daffamuhtar.taskcm.approval.utils.LoginState
//import com.daffamuhtar.taskcm.contacts.presentation.bird.BirdAppTheme
//import com.daffamuhtar.taskcm.contacts.presentation.bird.BirdsPage
//import com.daffamuhtar.taskcm.contacts.presentation.bird.com.daffamuhtar.taskcm.contacts.presentation.bird.BirdsViewModel
import com.daffamuhtar.taskcm.core.presentation.ContactsTheme
import com.daffamuhtar.taskcm.core.presentation.ImagePicker
import com.daffamuhtar.taskcm.di.AppModule
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
    imagePicker: ImagePicker,
    fcmToken: String?,
    loggedUserId: String?,
    userToken: String?,
    mainViewModel: MainViewModel?,
    stateMain: LoginState?,
    count: Int,
) {

    ContactsTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
    ){

        val snackbarHostState = remember { SnackbarHostState() }
        val scopeScaffold = rememberCoroutineScope()

        val viewModel2 =  getViewModel(
            key ="approval-list-screen",
            factory = viewModelFactory {
                ApprovalViewModel(loggedUserId, userToken, mainViewModel, stateMain, snackbarHostState)
            }
        )

        val state2 by viewModel2.state.collectAsState()

//        val viewModel =  getViewModel(
//            key ="contact-list-screen",
//            factory = viewModelFactory {
//                ContactListViewModel(appModule.contactDataSource)
//            }
//        )
//
//        val state by viewModel.state.collectAsState()
//

        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){

//            ContactListScreen(
//                state = state,
//                newContact = viewModel.newContact,
//                onEvent = viewModel::onEvent,
//                imagePicker = imagePicker
//            )



            ApprovalScreen(
                state = state2,
                onEvent = viewModel2::onEvent,
                fcmToken = fcmToken,
                loggedUserId = loggedUserId,
                userToken = userToken,
                approvalViewModel = viewModel2,
                mainViewModel = mainViewModel,
                stateMain = stateMain,
                snackbarHostState = snackbarHostState,
                scopeScaffold = scopeScaffold,
                count = count,

            )

        }
    }

}