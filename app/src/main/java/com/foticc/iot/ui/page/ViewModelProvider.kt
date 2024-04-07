package com.foticc.iot.ui.page

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomePageViewModel()
        }
    }
}
