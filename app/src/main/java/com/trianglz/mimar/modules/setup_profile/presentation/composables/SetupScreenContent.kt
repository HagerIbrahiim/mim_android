package com.trianglz.mimar.modules.setup_profile.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MultiColorPartiallyClickableText
import com.trianglz.mimar.common.presentation.compose_views.PrimarySecondaryButtonsRow
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.validation.PhoneNumberValidator
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.setup_profile.presentation.model.GenderRadioButtonOption

@Composable
fun SetupScreenContent(
    screenTitle: Int,
    fromHome: Boolean,
    firstName: () -> TextFieldState,
    lastName: () -> TextFieldState,
    phone: () -> TextFieldState,
    email: () -> TextFieldState,
    dob: () -> TextFieldState,
    isButtonValid: () -> Boolean,
    profileImage: () -> MutableState<String?>,
    secondaryBtnText: Int?,
    primaryBtnText: Int,
    onSelectPhotoClicked: () -> Unit,
    onSelectDateClicked: () -> Unit,
    onSelectDateTrailingIconClicked: () -> Unit,
    gendersList: () -> List<GenderRadioButtonOption>,
    selectedGenderRadioButtonOption: () -> MutableState<GenderRadioButtonOption?>,
    onBackPressed: () -> Unit,
    onTermsAndConditionsClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onGenderSelected: (GenderRadioButtonOption) -> Unit,
) {

    val termsAndConditionTextStyle: @Composable () -> TextStyle = remember {
        {
            MaterialTheme.typography.subtitle1.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
            )
        }
    }

    val dateIcon = remember(dob().textFieldValue.value.text) {
        if(dob().textFieldValue.value.text.isEmpty())
            R.drawable.ic_arrow_down
        else
            R.drawable.cancelled_icon
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .statusBarsPadding()
            .ifTrue(fromHome) {
                Modifier.calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
            }
    ) {

        val image = remember {
            {
                profileImage.invoke()
            }
        }

        HeaderShadow(dividerColor = Iron) { _ ->

            ScreenHeader(
                isAddPadding = { false },
                header = { StringWrapper(screenTitle) }) {
                onBackPressed.invoke()
            }
        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .calculateBottomPadding(),
            //contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    ProfileImageItem(
                        image = image.invoke().value ?: R.drawable.ic_profile_placeholder,
                        imageModifier = Modifier
                            .size(82.dp)
                            .clip(CircleShape), modifier = Modifier
                            .height(88.dp)
                            .width(82.dp), clickAction = { onSelectPhotoClicked.invoke() }

                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    BaseTextField(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        hint = R.string.enter_first_name,
                        label = R.string.first_name_star,
                        textState = firstName(),
                        textFieldModifier = Modifier,
                        validator = TextInputValidator.NotEmptyTextValidator(),
                        customTrailingIcon = null,
                        customLeadingIcon = R.drawable.ic_person,
                        onTrailingIconClicked = null
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))

                    BaseTextField(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        hint = R.string.enter_last_name,
                        label = R.string.last_name_star,
                        textState = lastName(),
                        textFieldModifier = Modifier,
                        validator = TextInputValidator.NotEmptyTextValidator(),
                        customTrailingIcon = null,
                        customLeadingIcon = R.drawable.ic_person,
                        onTrailingIconClicked = null
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))

                    BaseTextField(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email,
                        hint = R.string.enter_email,
                        label = com.trianglz.core.R.string.email,
                        textState = email(),
                        validator = TextInputValidator.OptionalEmailValidator(),
                        customLeadingIcon = R.drawable.ic_email,
                        onDone = { },
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))
                    BaseTextField(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        hint = R.string.select_date_of_birth,
                        label = R.string.date_of_birth,
                        textState = dob(),
                        isEnabled = false,
                        isSelectionTextField = true,
                        validator = TextInputValidator.FreeTextValidator(),
                        customLeadingIcon = R.drawable.ic_calendar,
                        customTrailingIcon = dateIcon,
                        onSelectionTextFieldClicked = {
                            onSelectDateClicked.invoke()
                        },
                        onTrailingIconClicked = {
                            onSelectDateTrailingIconClicked.invoke()
                        },
                        onDone = {

                        },
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))

                    BaseTextField(
                        imeAction = ImeAction.Next,
                        isEnabled = false,
                        keyboardType = KeyboardType.Phone,
                        hint = com.trianglz.core.R.string.phone_number,
                        label = R.string.phone_number_star,
                        textState = phone(),
                        textFieldModifier = Modifier,
                        validator = PhoneNumberValidator(),
                        onLeadingIconClicked = null
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))
                }
                GenderGroupComposable(
                    selectedGenderRadioButtonOption,
                    gendersList,
                    Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideXSmall)
                ) {
                    onGenderSelected.invoke(it)
                }

                Column(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!fromHome) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(
                            color = MaterialTheme.colors.background, thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        MultiColorPartiallyClickableText(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = MaterialTheme.dimens.screenGuideXSmall
                                ),
                            firstText = stringResource(id = R.string.by_clicking_next),
                            firstColor = MaterialTheme.colors.primary,
                            secondText = stringResource(id = R.string.terms_and_conditions),
                            secondColor = MaterialTheme.colors.secondary,
                            //  textAlign = TextAlign.Start,
                            style = termsAndConditionTextStyle(),
                            secondTextStyle = termsAndConditionTextStyle().copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            maxLines = Int.MAX_VALUE
                        ) {
                            onTermsAndConditionsClicked.invoke()
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))

                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))

                    PrimarySecondaryButtonsRow(
                        modifier = Modifier,
                        secondaryText = secondaryBtnText,
                        primaryText = primaryBtnText,
                        onSecondaryBtnClicked = onBackPressed,
                        isPrimaryBtnEnabled = isButtonValid,
                        onPrimaryBtnClicked = onNextClicked
                    )
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideDefault))

            }

        }
    }
}
