package com.example.waterbalancemonitor.domain.usecase.catalog

import com.example.waterbalancemonitor.domain.model.Vessel
import com.example.waterbalancemonitor.domain.repository.VesselRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveVesselsUseCase @Inject constructor(
    private val vesselRepository: VesselRepository
) {
    operator fun invoke(): Flow<List<Vessel>> = vesselRepository.observeAll()
}
