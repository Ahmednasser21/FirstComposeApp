package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.local.LocalProduct

sealed class DomainState {
    data class OnSuccess(val localProductList: List<LocalProduct>) : DomainState()
    data class OnFailed(val error: String) : DomainState()
}
