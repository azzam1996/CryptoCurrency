package com.azzam.cryptocurrency.currencies.presentation.utils

import androidx.annotation.StringRes

interface CommonUIEvent {
    data class ShowStringResource(@StringRes val message: Int) : CommonUIEvent
    data class ShowStringMessage(val message: String?) : CommonUIEvent
}