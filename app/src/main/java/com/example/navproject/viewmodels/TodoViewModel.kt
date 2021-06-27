package com.example.navproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navproject.constants.AlertType
import com.example.navproject.models.AlertModel
import com.example.navproject.screens.HomeFragment.Companion.PRICE_DONE_UP

class TodoViewModel : ViewModel() {
    var mListDataMockAPI: MutableLiveData<ArrayList<AlertModel>> = MutableLiveData()
    val mListOptionAlertLiveData: MutableLiveData<ArrayList<AlertModel>> = MutableLiveData()
    private val alertMap = hashMapOf(
        "last_done_up" to 0,
        "bid_price_up" to 0,
        "ask_price_up" to 0,
        "last_done_down" to 1,
        "bid_price_down" to 1,
        "ask_price_down" to 1
    )

    init {
        initOptionAlert()
        mockDataAPI()
    }

    private fun mockDataAPI() {
        val dataMock = ArrayList<AlertModel>()
        dataMock.add(AlertModel("bid_price_down", "ask price", alertID = "22", alertValue = "50"))
        dataMock.add(AlertModel("pc_change_up", "Chane %", alertID = "16", alertValue = "24"))
        dataMock.add(AlertModel("vol_up", "Chane %", alertID = "5", alertValue = "7"))
        mListDataMockAPI.value = dataMock
    }

    private fun initOptionAlert() {
        val listOption = ArrayList<AlertModel>()
        listOption.add(AlertModel(alertKey = 0, alertField = "last done"))
        listOption.add(AlertModel(alertKey = 1, alertField = "bid price"))
        listOption.add(AlertModel(alertKey = 2, alertField = "ask price"))
        mListOptionAlertLiveData.value = listOption
    }

    fun getAlertType(alertKey: Int?, optionKey: Int? = 0): AlertType? {
        return AlertType.values().let { list ->
            if (alertKey == PRICE_DONE_UP) list.firstOrNull { it.type.alertKey == optionKey }
            else list.lastOrNull { it.type.alertKey == optionKey }
        }
    }

    fun getDataFromAlert(alertKey: Int? = null, type: String? = null): AlertModel? {
        return mListDataMockAPI.value?.let { list ->
            if (type.isNullOrEmpty()) list.find { alertMap[it.alertType] == alertKey }
            else { list.find { it.alertType == type } }
        }
    }

    fun getCurrentKeyAlert(): Int? {
        return AlertType.values().find { list ->
            list.type.alertType == mListDataMockAPI.value?.find { alertMap.containsKey(it.alertType) }?.alertType
        }?.type?.alertKey ?: 0
    }
}