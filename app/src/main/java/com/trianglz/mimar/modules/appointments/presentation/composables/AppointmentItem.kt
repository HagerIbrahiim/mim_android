package com.trianglz.mimar.modules.appointments.presentation.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentDate.Companion.toAppointmentDate
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentItemType.*
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel

@Composable
fun AppointmentItem(appointment: () -> AppointmentUIModel, modifier: Modifier = Modifier) {

    val item = remember(appointment().showShimmer) {
        appointment.invoke()
    }

    val mainText : @Composable () -> String = remember(item.type) {
        {
            if (item.type is AppointmentList)
                item.branchName ?:""
            else
                "${stringResource(id = R.string.appointment)} ${item.id}"
        }
    }

    val secondaryTitle = remember(item.type) {
        if (item.type is AppointmentList)
            "#${item.appointmentNumber}"
        else
            item.branchName ?:""
    }

    val secondaryIcon = remember(item.type) {
        if (item.type is AppointmentList)
            R.drawable.ic_calendar_icon
        else
            R.drawable.branch_icon
    }

    val context = LocalContext.current

    Box(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .background(Color.White)
                .clickable { item.id.let { item.onClick.invoke(it) } }
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .align(Alignment.TopStart),
            ) {
                VerticalDateItem({ item.date?.toAppointmentDate() }, {item.showShimmer} )
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsXSmall),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.dimens.innerPaddingLarge)
                ) {
                    Text(
                        text = mainText(),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W700
                        ),
                        modifier = Modifier.shimmer(item.showShimmer, .8F)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TextStartsWithIcon(
                        drawableRes = secondaryIcon,
                        data = secondaryTitle,
                        iconTint = MaterialTheme.colors.secondary,
                        modifier = Modifier.shimmer(item.showShimmer)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item.location?.let {
                            TextStartsWithIcon(
                                drawableRes = R.drawable.ic_location,
                                data = it.name.getString(context),
                                iconTint = MaterialTheme.colors.secondary,
                                modifier = Modifier
                                    .weight(1F)
                                    .shimmer(item.showShimmer)
                            )
                        }


                        item.startsAt?.let {
                            TextStartsWithIcon(
                                drawableRes = R.drawable.ic_timer,
                                data = it,
                                modifier = Modifier.shimmer(item.showShimmer)
                            )
                        }


                    }


                }
            }
        }
    }
}
