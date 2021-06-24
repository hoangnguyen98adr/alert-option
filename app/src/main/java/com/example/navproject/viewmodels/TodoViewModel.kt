package com.example.navproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navproject.constants.TodoEnum
import com.example.navproject.models.AlertModel

class TodoViewModel : ViewModel() {
    var mListOptionTodoLiveData: MutableLiveData<ArrayList<AlertModel>> = MutableLiveData()
    var mListAlertFieldLiveData: MutableLiveData<ArrayList<TodoEnum.AlertType>> = MutableLiveData()
    var mListDataMockAPI: MutableLiveData<ArrayList<AlertModel>> = MutableLiveData()
    var mIsPriceUp: Boolean = false
    var mIsPriceDown: Boolean = false

    init {
        initData()
        mockDataAPI()
    }

    private fun mockDataAPI() {
        val dataMock = ArrayList<AlertModel>()
        dataMock.add(AlertModel("LastDoneUp", "LastPrice", alertID = "14", alertValue = 250))
        dataMock.add(AlertModel("LastDoneDown", "LastPrice", alertID = "15", alertValue = 15))
        dataMock.add(AlertModel("BidPriceUp", "BidPrice", alertID = "16", alertValue = 24))
        mListDataMockAPI.value = dataMock
    }

    private fun initData() {
        var listOptions = ArrayList<AlertModel>()
        for (item in TodoEnum.AlertType.values()) {
            listOptions.add(item.type)
        }
        listOptions = listOptions.distinctBy { it.alertField } as ArrayList<AlertModel>
        mListOptionTodoLiveData.value = listOptions
    }

    fun getAlertType(type: String) {
        mListDataMockAPI.value?.filter { it.alertField == type }?.forEach {
            for (item in TodoEnum.AlertType.values()) {
                item.type.alertID = it.alertID
                item.type.alertValue = it.alertValue
            }
        }
        mListAlertFieldLiveData.value = TodoEnum.AlertType.values().filter { it.type.alertField == type } as ArrayList<TodoEnum.AlertType>
    }
}