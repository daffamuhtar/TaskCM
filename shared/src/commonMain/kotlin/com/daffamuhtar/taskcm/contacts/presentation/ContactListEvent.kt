package com.daffamuhtar.taskcm.contacts.presentation

import com.daffamuhtar.taskcm.contacts.domain.Contact

sealed interface ContactListEvent {
    object OnAddNewContactClick : ContactListEvent
    object DismissContact : ContactListEvent
    data class OnFirstNameChanged(val value: String) : ContactListEvent
    data class OnLastNameChanged(val value: String) : ContactListEvent
    data class OnEmailChanged(val value: String) : ContactListEvent
    data class OnPhoneNumberChanged(val value: String) : ContactListEvent
    class OnPhotoPicked(val value: ByteArray) : ContactListEvent
    object OnAddphotoClicked : ContactListEvent
    object SaveContact : ContactListEvent
    data class SelectedContact(val contact: Contact) : ContactListEvent
    data class EditCotact(val contact: Contact) : ContactListEvent
    object DeleteContact : ContactListEvent

}