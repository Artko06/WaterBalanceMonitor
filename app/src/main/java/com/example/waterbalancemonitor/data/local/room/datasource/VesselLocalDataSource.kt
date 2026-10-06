package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.VesselDao
import com.example.waterbalancemonitor.data.local.room.entity.VesselEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VesselLocalDataSource @Inject constructor(
    private val vesselDao: VesselDao
) {

    fun observeAll(): Flow<List<VesselEntity>> = vesselDao.observeAll()

    suspend fun getAll(): List<VesselEntity> = vesselDao.getAll()

    suspend fun getById(id: Long): VesselEntity? = vesselDao.getById(id)

    suspend fun insertAll(items: List<VesselEntity>) = vesselDao.insertAll(items)

    suspend fun upsert(item: VesselEntity) = vesselDao.upsert(item)

    suspend fun deleteById(id: Long) = vesselDao.deleteById(id)
}
