package com.trianglz.mimar.modules.user_home.presentation.model

import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R

sealed class BranchSectionType(val name: StringWrapper) {
    object Popular: BranchSectionType(StringWrapper(R.string.popular_providers_nearby))
    object Favorites: BranchSectionType(StringWrapper(R.string.my_favorites))
}
