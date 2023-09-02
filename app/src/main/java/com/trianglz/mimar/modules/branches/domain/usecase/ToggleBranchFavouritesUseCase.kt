package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import javax.inject.Inject

class ToggleBranchFavouritesUseCase @Inject constructor(
    private val repo: BranchesRepository,
    private val userModeHandler: UserModeHandler,
) {

    suspend fun execute(id: Int, addToFavourite: Boolean) {
        if (!userModeHandler.isGuest()) {
            repo.toggleBranchFavourites(id, addToFavourite)
        }
    }
}
