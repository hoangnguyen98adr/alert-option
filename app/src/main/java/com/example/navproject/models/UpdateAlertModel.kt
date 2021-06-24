package com.example.navproject.models

data class UpdateAlertModel(
    val alertID: String ?= null,
    val alertSwitch: String,
    val alertType: String,
    val alertValue: String ?= null
)