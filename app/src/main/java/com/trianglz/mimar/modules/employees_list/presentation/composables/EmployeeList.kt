package com.trianglz.mimar.modules.employees_list.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel


@Composable
fun EmployeeList(list: () -> List<EmployeeUIModel>) {

    val bottomPadding : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.screenGuideDefault + MaterialTheme.dimens.buttonHeight
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = MaterialTheme.dimens.screenGuideDefault,
            bottom = bottomPadding()
        )
    ) {

        if (list().isEmpty()) {
            item {
                MimarPlaceholder(
                    modifier = { Modifier.fillParentMaxSize() },
                    animationFile =  R.raw.search_placeholder ,
                    titleFirstText = { R.string.oops },
                    titleSecondText = { R.string.no_available_staff },
                )
            }
        } else {
            list().forEachIndexed { index, item ->
                item {
                    val isLastEmployee by remember {
                        derivedStateOf {
                            list().size.minus(1) == index
                        }
                    }

                    Column {
                        SelectEmployeeItem { item }
                        if (!isLastEmployee) {
                            HorizontalDivider(
                                padding = PaddingValues(
                                    vertical = 0.dp,
                                    horizontal = MaterialTheme.dimens.screenGuideDefault
                                )
                            )
                        }

                    }
                }


            }
        }

    }


}