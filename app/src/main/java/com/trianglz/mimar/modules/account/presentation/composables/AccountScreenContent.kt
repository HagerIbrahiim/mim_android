package com.trianglz.mimar.modules.account.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.modules.account.presentation.model.BaseSettingModel
import com.trianglz.mimar.modules.account.presentation.model.ProfileMainItemModel
import com.trianglz.mimar.modules.account.presentation.model.ProfileTitleModel
import com.trianglz.mimar.common.presentation.compose_views.ProfileHeader
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.models.ProfileHeaderInfoModel
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge

@Composable
fun AccountScreenContent(settingList: List<BaseSettingModel>) {

    val title = remember {
        if(getAppLocale() == Locales.ARABIC.code)
            R.string.my_account else R.string.profile
    }
    Box(
        modifier = Modifier
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {

        // screen content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ScreenHeader(
                header = { StringWrapper(title) },
                contentColor = MaterialTheme.colors.surface ,
                isAddBackButton = { false },
                isAddPadding = { false },
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .statusBarsPadding()
                    .padding(top = MaterialTheme.dimens.spaceBetweenItemsMedium)
                    .padding(bottom = MaterialTheme.dimens.spaceBetweenItemsXXLarge)
                    .background(MaterialTheme.colors.primary)
            )

            LazyColumn(modifier = Modifier) {
                items(settingList) {
                    when (it) {
                        is ProfileTitleModel -> {
                            ProfileTitle(it)
                        }

                        is ProfileMainItemModel -> {
                            ProfileItem(it)
                        }

                        is ProfileHeaderInfoModel -> {
                            ProfileHeader(it)
                        }
                    }
                }
            }

        }

    }
}