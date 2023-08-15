package com.daffamuhtar.taskcm.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.daffamuhtar.taskcm.contacts.domain.Contact
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactListViewModel : ViewModel() {

    private val _state = MutableStateFlow(ContactListState(
        contacts = contacts
    ))
    val state = _state.asStateFlow()

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent (event: ContactListEvent){

    }
}

private val contacts = (1..50).map {
    Contact(
        id = it.toLong(),
        firstName = "firstName $it",
        lastName = "lastName $it",
        email = "email $it",
        phoneNumber = "$it-81201921",
        photoBytes = null ,
    )

}