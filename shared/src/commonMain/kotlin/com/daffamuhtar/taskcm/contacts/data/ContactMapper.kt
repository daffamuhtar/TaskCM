package com.daffamuhtar.taskcm.contacts.data

import com.daffamuhtar.taskcm.contacts.domain.Contact
import com.daffamuhtar.taskcm.core.data.ImageStorage
import database.ContactEntity

suspend fun ContactEntity.toContact(imageStorage: ImageStorage): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photoBytes = imagePath?.let { imageStorage.getImage(it) }
    )
}