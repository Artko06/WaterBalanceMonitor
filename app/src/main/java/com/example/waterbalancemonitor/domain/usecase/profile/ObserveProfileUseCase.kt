package com.example.waterbalancemonitor.domain.usecase.profile

import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(): Flow<Profile?> = profileRepository.observeProfile()
}
