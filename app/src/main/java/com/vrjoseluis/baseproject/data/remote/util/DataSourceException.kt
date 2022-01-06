package com.vrjoseluis.baseproject.data.remote.util

import com.vrjoseluis.baseproject.data.model.AsyncError

class DataSourceException(val asyncError: AsyncError) : Exception() {
    override fun toString(): String {
        return asyncError.toString()
    }
}