package com.example.navproject.models

data class AlertModel(
    val alertType: String? = null,
    val alertField: String? = null,
    var alertValue: String? = "0",
    var alertID: String? = null,
    val alertKey: Int ?= null
)