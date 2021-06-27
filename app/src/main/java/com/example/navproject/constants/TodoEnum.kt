package com.example.navproject.constants

import com.example.navproject.models.AlertModel

enum class AlertType(val type: AlertModel) {
    LAST_DONE_UP(AlertModel("last_done_up", alertKey = 0)),
    LAST_DONE_DOWN(AlertModel("last_done_down", alertKey = 0)),
    BID_PRICE_UP(AlertModel("bid_price_up", alertKey = 1)),
    BID_PRICE_DOWN(AlertModel("bid_price_down", alertKey = 1)),
    ASK_PRICE_UP(AlertModel("ask_price_up", alertKey = 2)),
    ASK_PRICE_DOWN(AlertModel("ask_price_down", alertKey = 2)),
    CHANGE_UP(AlertModel("pc_change_up")),
    CHANGE_DOWN(AlertModel("pc_change_down")),
    VOL_UP(AlertModel("vol_up"))
}