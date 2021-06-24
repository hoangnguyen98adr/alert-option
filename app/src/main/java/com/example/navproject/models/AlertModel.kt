package com.example.navproject.models

data class AlertModel(
    val alertType: String? = null,
    val alertField: String? = null,
    val alertDisplay: String? = null,
    var alertValue: Int? = 0,
    var alertID: String? = null
)