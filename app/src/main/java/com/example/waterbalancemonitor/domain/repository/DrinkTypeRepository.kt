package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.DrinkType
import kotlinx.coroutines.flow.Flow

interface DrinkTypeRepository {

    fun observeAll(): Flow<List<DrinkType>>

    suspend fun getAll(): List<DrinkType>

    suspend fun getById(id: Long): DrinkType?

    suspend fun save(drinkType: DrinkType)

    suspend fun saveAll(drinkTypes: List<DrinkType>)

    suspend fun deleteById(id: Long)
}
