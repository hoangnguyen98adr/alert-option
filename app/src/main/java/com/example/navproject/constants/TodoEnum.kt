package com.example.navproject.constants

import com.example.navproject.models.AlertModel

class TodoEnum {
    enum class AlertType(val type: AlertModel) {
        LAST_DONE_UP(
            AlertModel(
                "LastDoneUp",
                "LastPrice",
                "Last done"
            )
        ),
        LAST_DONE_DOWN(
            AlertModel(
                "LastDoneDown",
                "LastPrice",
                "Last done"
            )
        ),
        BID_PRICE_UP(
            AlertModel(
                "BidPriceUp",
                "BidPrice",
                "Bid price"
            )
        ),
        BID_PRICE_DOWN(
            AlertModel(
                "BidPriceDown",
                "BidPrice",
                "Bid price"
            )
        ),
        ASK_PRICE_UP(
            AlertModel(
                "AskPriceUp",
                "AskPrice",
                "Ask price"
            )
        ),
        ASK_PRICE_DOWN(
            AlertModel(
                "AskPriceDown",
                "AskPrice",
                "Ask price"
            )
        )
    }
}