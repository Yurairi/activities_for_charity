package com.example.hack

import com.example.hack.Common.Companion.STATE_ERROR
import com.example.hack.Common.Companion.STATE_LOADED
import com.example.hack.Common.Companion.STATE_LOADING
import com.example.hack.Common.Companion.STATE_UNDEF
import com.example.hack.network.dto.CompanyFull


data class DataHolder<T>(var data: T? = null, var error: String? = null, @Common.Companion.States var state: Int = STATE_UNDEF) {

    companion object {
        fun <T> error(error: String? = null) = DataHolder<T>(state = STATE_ERROR, error = error)
        fun <T> loading() = DataHolder<T>(state = STATE_LOADING)
        fun <T> withData(data: T, metadata: Map<String, Any>? = null)
                = DataHolder(data = data, state = STATE_LOADED).apply {
            this.metadata.clear()
            metadata?.let { this.metadata.putAll(it) }
        }
    }

    val metadata: MutableMap<String, Any> = mutableMapOf()
}
