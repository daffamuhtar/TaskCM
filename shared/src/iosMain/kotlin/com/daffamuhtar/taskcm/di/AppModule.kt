package com.daffamuhtar.taskcm.di

import com.daffamuhtar.taskcm.contacts.data.SqlDelightContactDataSource
import com.daffamuhtar.taskcm.contacts.domain.ContactDataSource
import com.daffamuhtar.taskcm.core.data.DatabaseDriverFactory
import com.daffamuhtar.taskcm.core.data.ImageStorage
import com.daffamuhtar.taskcm.database.ContactDatabase

actual class AppModule {

    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}