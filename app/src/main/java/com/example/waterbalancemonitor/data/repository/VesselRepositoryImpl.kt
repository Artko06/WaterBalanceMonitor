package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.VesselLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.Vessel
import com.example.waterbalancemonitor.domain.repository.VesselRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VesselRepositoryImpl @Inject constructor(
    private val vesselLocalDataSource: VesselLocalDataSource
) : VesselRepository {

    override fun observeAll(): Flow<List<Vessel>> =
        vesselLocalDataSource.observeAll().map { items -> items.map { it.toDomain() } }

    override suspend fun getAll(): List<Vessel> =
        vesselLocalDataSource.getAll().map { it.toDomain() }

    override suspend fun getById(id: Long): Vessel? =
        vesselLocalDataSource.getById(id)?.toDomain()

    override suspend fun save(vessel: Vessel) =
        vesselLocalDataSource.upsert(vessel.toData())

    override suspend fun saveAll(vessels: List<Vessel>) =
        vesselLocalDataSource.insertAll(vessels.map { it.toData() })

    override suspend fun deleteById(id: Long) = vesselLocalDataSource.deleteById(id)
}
