package com.foticc.iot.ui.page

import androidx.lifecycle.ViewModel
import com.foticc.iot.ScanApplication
import com.foticc.iot.database.dao.QuestionDao

class HomePageViewModel() : ViewModel() {

    val questionDao: QuestionDao = ScanApplication.getDatabase().questionDao();

    val list = questionDao.selectAll();

    fun search(searchText:String) {

    }

}