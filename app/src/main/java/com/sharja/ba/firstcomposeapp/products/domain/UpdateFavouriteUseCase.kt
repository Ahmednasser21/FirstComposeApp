package com.sharja.ba.firstcomposeapp.products.domain

import com.sharja.ba.firstcomposeapp.products.data.Repository
import javax.inject.Inject

class UpdateFavouriteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(productId: Int, isFav: Boolean) {
        repository.updateFavState(productId, !isFav)
    }
}
