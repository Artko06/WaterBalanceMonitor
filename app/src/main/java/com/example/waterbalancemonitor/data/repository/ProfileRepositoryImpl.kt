package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.ProfileLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileLocalDataSource: ProfileLocalDataSource
) : ProfileRepository {

    override fun observeProfile(): Flow<Profile?> =
        profileLocalDataSource.observeProfile().map { it?.toDomain() }

    override suspend fun getProfile(): Profile? =
        profileLocalDataSource.getProfile()?.toDomain()

    override suspend fun saveProfile(profile: Profile) =
        profileLocalDataSource.upsert(profile.toData())

    override suspend fun deleteProfile() = profileLocalDataSource.deleteAll()
}
