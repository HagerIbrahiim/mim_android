package com.trianglz.mimar.modules.employee_details.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider
import com.trianglz.mimar.modules.services.presentation.composables.ServiceItem
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

@Composable
fun EmployeeServiceItem(
    title: () -> StringWrapper?,
    data: () -> List<ServiceUIModel>
) {

    Text(
        text = stringResource(id = R.string.service_title, title()?.getString(LocalContext.current) ?:""),
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.W500
        ),
        maxLines = 1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,

    )

    data().forEachIndexed { index, item ->
        val isLastService by remember {
            derivedStateOf {
                data().size.minus(1) == index
            }
        }
        ServiceItem(
            service = { item },
            modifier = Modifier,
            horizontalPadding = 0.dp
        )
        if (!isLastService) {
            HorizontalDivider(padding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideDefault))
        }

    }

}