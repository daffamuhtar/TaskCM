package com.daffamuhtar.taskcm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daffamuhtar.taskcm.contacts.presentation.ContactListScreen
import com.daffamuhtar.taskcm.core.presentation.ContactsTheme
import org.jetbrains.skia.Surface

@Composable
fun App(
    darkTheme : Boolean,
    dynamicColor : Boolean,

) {

    ContactsTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
    ){
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
             
        }
    }

}