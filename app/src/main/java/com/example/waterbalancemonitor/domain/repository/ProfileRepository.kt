package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun observeProfile(): Flow<Profile?>

    suspend fun getProfile(): Profile?

    suspend fun saveProfile(profile: Profile)

    suspend fun deleteProfile()
}
