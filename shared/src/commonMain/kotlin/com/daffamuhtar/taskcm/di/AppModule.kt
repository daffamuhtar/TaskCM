package com.daffamuhtar.taskcm.di

import com.daffamuhtar.taskcm.contacts.domain.ContactDataSource

expect class AppModule {
    val contactDataSource: ContactDataSource
}