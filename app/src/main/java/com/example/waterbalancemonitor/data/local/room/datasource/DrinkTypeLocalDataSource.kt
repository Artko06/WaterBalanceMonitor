package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.DrinkTypeDao
import com.example.waterbalancemonitor.data.local.room.entity.DrinkTypeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkTypeLocalDataSource @Inject constructor(
    private val drinkTypeDao: DrinkTypeDao
) {

    fun observeAll(): Flow<List<DrinkTypeEntity>> = drinkTypeDao.observeAll()

    suspend fun getAll(): List<DrinkTypeEntity> = drinkTypeDao.getAll()

    suspend fun getById(id: Long): DrinkTypeEntity? = drinkTypeDao.getById(id)

    suspend fun insertAll(items: List<DrinkTypeEntity>) = drinkTypeDao.insertAll(items)

    suspend fun upsert(item: DrinkTypeEntity) = drinkTypeDao.upsert(item)

    suspend fun deleteById(id: Long) = drinkTypeDao.deleteById(id)
}
