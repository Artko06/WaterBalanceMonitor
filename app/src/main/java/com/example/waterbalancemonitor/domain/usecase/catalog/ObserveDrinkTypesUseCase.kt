package com.example.waterbalancemonitor.domain.usecase.catalog

import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.domain.repository.DrinkTypeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDrinkTypesUseCase @Inject constructor(
    private val drinkTypeRepository: DrinkTypeRepository
) {
    operator fun invoke(): Flow<List<DrinkType>> = drinkTypeRepository.observeAll()
}
