package com.trianglz.mimar.modules.setup_profile.presentation.composables


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Charcoal
import com.trianglz.mimar.common.presentation.ui.theme.RomanSilver
import com.trianglz.mimar.common.presentation.ui.theme.Shapes
import com.trianglz.mimar.modules.setup_profile.presentation.model.GenderRadioButtonOption

@Composable
fun GenderGroupComposable(
    selectedGenderRadioButtonOption: () -> MutableState<GenderRadioButtonOption?>,
    gendersList: () -> List<GenderRadioButtonOption>,
    modifier: Modifier,
    onGenderSelected: (GenderRadioButtonOption) -> Unit,
) {

    Column(modifier) {

        Text(
            text = stringResource(id = R.string.gender),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W400),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Start, maxLines = 1, overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.screenGuideXSmall)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.selectableGroup()) {

            gendersList().forEach { option ->
                val isSelected = remember(selectedGenderRadioButtonOption().value) {
                    selectedGenderRadioButtonOption().value == option && selectedGenderRadioButtonOption().value != null
                }
                val color = remember(isSelected, selectedGenderRadioButtonOption().value) {
                    if (isSelected)
                        Charcoal
                    else
                        RomanSilver
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .clip(Shapes.small)
                            .selectable(
                                selected = isSelected,
                                onClick = {
                                    onGenderSelected.invoke(option)
                                },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            modifier = Modifier.padding(
                                start = MaterialTheme.dimens.screenGuideXSmall,
                                end = 10.dp
                            ),
                            selected = isSelected,
                            onClick = null, // null recommended for accessibility with screen readers
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.primary,
                                unselectedColor = MaterialTheme.colors.onBackground
                            )

                        )

                        Icon(
                            modifier = Modifier
                                .padding(end = 8.dp),
                            painter = painterResource(option.icon),
                            tint = color,
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.padding(end = MaterialTheme.dimens.screenGuideXSmall),
                            text = stringResource(id = option.name),
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = color,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }
                }
            }
        }
    }
}

