package com.trianglz.mimar.modules.add_address.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.validation.NumberValidator
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState

@Composable
fun AddAddressTextFields(
    addressTitle: () -> TextFieldState,
    country: () -> TextFieldState,
    city: () -> TextFieldState,
    streetName: () -> TextFieldState,
    buildingNum: () -> TextFieldState,
    district: () -> TextFieldState,
    secondaryNum: () -> TextFieldState,
    landmarkNotes: () -> TextFieldState,
    isButtonValid: () -> Boolean,
    onSaveClicked: () -> Unit
) {



    Column(
        Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault),

                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.screenGuideLarge)
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge ))

        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_address_title,
            label = R.string.address_title,
            textState = addressTitle(),
            validator = TextInputValidator.NotEmptyTextValidator(),
        )

        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_country,
            label = R.string.country,
            textState = country(),
            isEnabled = false,
            validator = TextInputValidator.NotEmptyTextValidator(),
        )

        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_city,
            label = R.string.city,
            textState = city(),
            isEnabled = false,
            validator = TextInputValidator.NotEmptyTextValidator(),
        )


        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_street_name,
            label = R.string.street_name,
            textState = streetName(),
            validator = TextInputValidator.NotEmptyTextValidator(),
        )


        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_building_number,
            label = R.string.building_number,
            textState = buildingNum(),
            validator = TextInputValidator.NotEmptyTextValidator(),
        )


        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_district,
            label = R.string.district,
            textState = district(),
            validator = TextInputValidator.NotEmptyTextValidator(),
        )


        BaseTextField(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number,
            hint = R.string.secondary_number,
            label = R.string.secondary_number,
            textState = secondaryNum(),
            validator = NumberValidator(true),
        )


        BaseTextField(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            hint = R.string.enter_landmark,
            label = R.string.landmark,
            textState = landmarkNotes(),
            validator = TextInputValidator.FreeTextValidator(),
        )


        BaseComposeMainUIComponents.LocalMainComponent.AppButton(
            modifier = Modifier,
            text = R.string.save,
            enabled = isButtonValid(),
            textStyle = MaterialTheme.typography.button,
            isAddAlphaToDisabledButton = true,
            disabledColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary,
            onClick = { if(isButtonValid()) onSaveClicked() }
        )

    }

}

