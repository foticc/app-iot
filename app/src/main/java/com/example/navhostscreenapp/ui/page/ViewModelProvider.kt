package com.example.navhostscreenapp.ui.page

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomePageViewModel()
        }
    }
}
