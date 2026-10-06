package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.Vessel
import kotlinx.coroutines.flow.Flow

interface VesselRepository {

    fun observeAll(): Flow<List<Vessel>>

    suspend fun getAll(): List<Vessel>

    suspend fun getById(id: Long): Vessel?

    suspend fun save(vessel: Vessel)

    suspend fun saveAll(vessels: List<Vessel>)

    suspend fun deleteById(id: Long)
}
