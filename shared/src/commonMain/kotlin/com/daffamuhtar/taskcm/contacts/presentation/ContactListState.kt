package com.daffamuhtar.taskcm.contacts.presentation

import com.daffamuhtar.taskcm.contacts.domain.Contact

data class ContactListState(
    val contacts : List<Contact> = emptyList(),
    val recentlyAddedContact: List<Contact> = emptyList(),
    val selectedContact: Contact? = null,
    val isAddCOntactSheetOpen: Boolean = false,
    val isSelectedContactSheetOpen: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null,
)