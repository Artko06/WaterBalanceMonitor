package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.ProfileDao
import com.example.waterbalancemonitor.data.local.room.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileLocalDataSource @Inject constructor(
    private val profileDao: ProfileDao
) {

    fun observeProfile(): Flow<ProfileEntity?> = profileDao.observeProfile()

    suspend fun getProfile(): ProfileEntity? = profileDao.getProfile()

    suspend fun upsert(profile: ProfileEntity) = profileDao.upsert(profile)

    suspend fun deleteAll() = profileDao.deleteAll()
}
