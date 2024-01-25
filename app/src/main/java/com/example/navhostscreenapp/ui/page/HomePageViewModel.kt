package com.example.navhostscreenapp.ui.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navhostscreenapp.ScanApplication
import com.example.navhostscreenapp.database.AppDataBase
import com.example.navhostscreenapp.database.dao.QuestionDao

class HomePageViewModel() : ViewModel() {

    val questionDao: QuestionDao = ScanApplication.getDatabase().questionDao();

    val list = questionDao.selectAll();

    fun search(searchText:String) {

    }

}