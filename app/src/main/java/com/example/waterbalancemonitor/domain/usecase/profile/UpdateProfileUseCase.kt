package com.example.waterbalancemonitor.domain.usecase.profile

import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) = profileRepository.saveProfile(profile)
}
