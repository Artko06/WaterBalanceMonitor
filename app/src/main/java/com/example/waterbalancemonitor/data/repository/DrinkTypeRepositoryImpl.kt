package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.DrinkTypeLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.domain.repository.DrinkTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DrinkTypeRepositoryImpl @Inject constructor(
    private val drinkTypeLocalDataSource: DrinkTypeLocalDataSource
) : DrinkTypeRepository {

    override fun observeAll(): Flow<List<DrinkType>> =
        drinkTypeLocalDataSource.observeAll().map { items -> items.map { it.toDomain() } }

    override suspend fun getAll(): List<DrinkType> =
        drinkTypeLocalDataSource.getAll().map { it.toDomain() }

    override suspend fun getById(id: Long): DrinkType? =
        drinkTypeLocalDataSource.getById(id)?.toDomain()

    override suspend fun save(drinkType: DrinkType) =
        drinkTypeLocalDataSource.upsert(drinkType.toData())

    override suspend fun saveAll(drinkTypes: List<DrinkType>) =
        drinkTypeLocalDataSource.insertAll(drinkTypes.map { it.toData() })

    override suspend fun deleteById(id: Long) = drinkTypeLocalDataSource.deleteById(id)
}
