package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.searchBackground
import com.trianglz.mimar.common.presentation.ui.theme.searchHeight


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchText: () -> MutableState<String?>,
    onSearchTextChanged: ((String) -> Unit)? = null,
    enabled: () -> Boolean = { true },
    addPadding: () -> Boolean = { true },
    elevation: @Composable () -> Dp = { 0.dp },
    background: @Composable () -> Color = { MaterialTheme.colors.searchBackground },
    hintColor: @Composable () -> Color = { MaterialTheme.colors.onBackground },
    textColor: @Composable () -> Color = { MaterialTheme.colors.primary },
    hintText: @Composable () -> String = { stringResource(id = R.string.search__) },
    addSearchLeadingIcon: () -> Boolean = { true },
    maxLines: () -> Int = { 1 },
    hasFilterIcon: Boolean = false,
    hasFilterData: () -> Boolean = { false },
    onFilterClicked: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val isClickable = remember {
        onClick != null || enabled()
    }
    val showClearTextBtn = remember {
        derivedStateOf {
            searchText.invoke().value?.isNotEmpty() ?: false
        }
    }.value
    val singleLine = remember {
        maxLines() == 1
    }


    val paddingModifier : @Composable () -> Modifier = remember {
        {
            if (addPadding()) Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) else Modifier
        }
    }

    val baseLeadingIcon: (@Composable () -> Unit)? = remember {
        if (addSearchLeadingIcon()) {
            {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }
        } else null
    }

    val baseTrailingIcon: (@Composable () -> Unit)? = remember(showClearTextBtn) {
        if (enabled()) {
            {
                AnimatedVisibility(showClearTextBtn, enter = fadeIn(), exit = fadeOut()) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { searchText.invoke().value = "" }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_clear_text),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        } else null
    }

    val filterTintColor : @Composable () -> Color = remember(hasFilterData()) {
        {
            if (hasFilterData()) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
        }
    }


    ProvideTextStyle(value = MaterialTheme.typography.body2.copy(color = textColor.invoke())) {
        Row(modifier = Modifier.then(paddingModifier()), horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsXSmall)) {
            OutlinedTextField(
                value = searchText.invoke().value ?:"",
                maxLines = maxLines(),
                singleLine = singleLine,
                onValueChange = {
                    searchText.invoke().value = it
                    onSearchTextChanged?.invoke(it)
                },
                leadingIcon = baseLeadingIcon,
                trailingIcon = baseTrailingIcon,
                placeholder = {
                    Text(
                        text = hintText(),
                        color = hintColor.invoke(),
                        style = MaterialTheme.typography.body2
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = elevation(),
                        shape = MaterialTheme.shapes.medium,
                    )
                    .fillMaxWidth()
                    .defaultMinSize(
                        minWidth = TextFieldDefaults.MinWidth,
                        minHeight = MaterialTheme.dimens.searchHeight
                    )
                    .height(MaterialTheme.dimens.searchHeight)
                    .background(
                        background.invoke(),
                        shape = MaterialTheme.shapes.medium
                    )

                    .border(
                        width = 0.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = Color.Transparent
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable(enabled = isClickable) {
                        onClick?.invoke()
                    }
                    .then(modifier),
                enabled = enabled(),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    backgroundColor = background.invoke(),
                    disabledIndicatorColor = Color.Transparent
                )

            )
            if (hasFilterIcon) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = elevation(),
                            shape = MaterialTheme.shapes.medium,
                        )
                        .size(MaterialTheme.dimens.searchHeight)
                        .background(
                            background.invoke(),
                            shape = MaterialTheme.shapes.medium
                        )

                        .border(
                            width = 0.dp,
                            shape = MaterialTheme.shapes.medium,
                            color = Color.Transparent
                        )
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            onFilterClicked?.invoke()
                        },
                ) {
                    ImageFromRes(
                        imageId = R.drawable.ic_filter,
                        modifier = Modifier.align(Alignment.Center),
                        tintColor = filterTintColor()
                    )
                }
            }
        }

    }
}
