package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUpdatesUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetBranchFavouritesUpdates @Inject constructor(private val repo: BranchesRepository) :
    BaseUpdatesUseCase<BranchDomainModel> {
    override suspend fun execute(): Flow<BranchDomainModel> {
        return repo.getBranchFavouritesUpdates()
    }
}