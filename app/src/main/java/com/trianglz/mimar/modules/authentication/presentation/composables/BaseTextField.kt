package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.PeriwinkleCrayola
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel

// region BaseTextField
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun BaseTextField(
    textState: TextFieldState,
    textFieldModifier: Modifier = Modifier,
    validator: TextInputValidator,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    customLeadingIcon: Int? = null,
    customTrailingIcon: Int? = null,
    isEnabled: Boolean = true,
    hint: Int? = null,
    label: Int? = null,
    maxLines: Int = 1,
    backgroundColor: Color = MaterialTheme.colors.onPrimary,
    customHeight: Dp = 82.dp,
    isSelectionTextField: Boolean = false,
    stopValidUpdate : Boolean = false,
    onDone: (() -> Unit)? = {},
    onTrailingIconClicked: (() -> Unit)? = {},
    onSelectionTextFieldClicked: (() -> Unit)? = { onTrailingIconClicked?.invoke() },
    onLeadingIconClicked: (() -> Unit)? = {},
) {

    val context = LocalContext.current


    var textFieldValue by remember {
        textState.textFieldValue
    }

    var userHasChangedText by remember {
        textState.isUserChangedText
    }

    var hasFocus by remember {
        textState.hasFocus
    }

    var isValid by remember {
        textState.isValid
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val countryCode by remember {
        textState.countryCode
    }

    val singleLine = remember {
        maxLines == 1
    }

    val requiredHeight = remember {
        if (maxLines == 1) 48.dp else customHeight
    }

    val errorColor = MaterialTheme.colors.error


    val borderColor by remember {
        derivedStateOf {
            if (!isValid && userHasChangedText) errorColor else {
                if (hasFocus) PeriwinkleCrayola else Color.Transparent
            }
        }
    }


    val textFieldBackgroundColor = remember {
        if (isEnabled || isSelectionTextField) backgroundColor else backgroundColor.copy(alpha = .3F)
    }

    val clickableModifier = remember {
        if(isSelectionTextField) Modifier.clickable(onClick = onSelectionTextFieldClicked ?:{}) else Modifier
    }

    val errorMessage by remember {
        derivedStateOf {
            validator.isValid(textFieldValue.text)
        }
    }

    val passwordIcon by remember {
        derivedStateOf {
            if (isPasswordVisible) R.drawable.ic_hide_password else R.drawable.ic_show_password
        }
    }


    val visualTransformation by remember {
        derivedStateOf {
            if (keyboardType == KeyboardType.Password && !isPasswordVisible)
                PasswordVisualTransformation()
            else VisualTransformation.None
        }
    }

    val countryIcon = remember(countryCode) {
        CountryUIModel.getCountryImageResourceId(context, countryCode.second,)
    }

    val baseLeadingIcon: (@Composable () -> Unit)? by remember(countryCode) {
        derivedStateOf {
            if (keyboardType == KeyboardType.Password
                || keyboardType == KeyboardType.Phone
                || keyboardType == KeyboardType.Email
                || customLeadingIcon != null
            ) {
                {
                    if(keyboardType == KeyboardType.Phone)
                        CountryCodeIcon(
                            countryIcon = countryIcon ,
                            countryCode = countryCode.first,
                            onPhoneIconClicked = onLeadingIconClicked
                        )
                    else
                        TextFieldLeadingIcon(keyboardType = keyboardType, customLeadingIcon)
                }
            } else null
        }


    }


    val baseTrailingIcon: (@Composable () -> Unit)? by remember(textFieldValue.text) {
        derivedStateOf {
            if (keyboardType == KeyboardType.Password) {
                {
                    ShowPasswordIcon(icon = passwordIcon) {
                        isPasswordVisible = !isPasswordVisible
                    }
                }
            }

            else if (customTrailingIcon != null){
                {

                    IconButton(
                        onClick = onTrailingIconClicked ?: {},
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = customTrailingIcon),
                            contentDescription = "icon"
                        )
                    }

                }
            }

            else null
        }
    }

    val onTextFieldValueChanged: (TextFieldValue) -> Unit = remember {
        {
            textFieldValue = it
            userHasChangedText = true
            if(!stopValidUpdate) {
                isValid = validator.isValid(textFieldValue.text) == null
            }
        }
    }

    LaunchedEffect(key1 = textFieldValue,){
        if(!stopValidUpdate) {
            isValid = validator.isValid(textFieldValue.text) == null
        }

    }


    val onFocusEvent: (FocusState) -> Unit = remember {
        {
            hasFocus = it.isFocused
        }
    }

    val focusManager = LocalFocusManager.current


    val placeholder: @Composable () -> Unit = remember {
        {
            TextFieldHint(hint = hint)
        }
    }

    val textDirection = remember {
        if (getAppLocale() == Locales.ARABIC.code)
            TextDirection.Rtl
        else
            TextDirection.Ltr
    }

    val paddingStart = remember {
        if (baseLeadingIcon == null)
            16.dp
        else if(keyboardType == KeyboardType.Phone) 0.dp
        else 6.dp
    }

    val paddingEnd = remember {
        if (baseTrailingIcon == null) 16.dp else 6.dp
    }


    Column {
        if (label != null) {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W400 ),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(bottom = 14.dp))
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(requiredHeight)
                .clip(MaterialTheme.shapes.xSmall)
                .background(textFieldBackgroundColor)
                .border(1.dp, borderColor, MaterialTheme.shapes.xSmall)
                .then(clickableModifier)
                .padding(start = paddingStart, end = paddingEnd)
                .onFocusEvent(onFocusEvent = onFocusEvent)
                .then(textFieldModifier),

            value = textFieldValue,
            textStyle = MaterialTheme.typography.body2.copy(
                textDirection = textDirection,
                fontWeight = FontWeight.W400),
            onValueChange = onTextFieldValueChanged,
            enabled = isEnabled,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = {
                    focusManager.clearFocus()
                    onDone?.invoke()
                }
            ),
            visualTransformation = visualTransformation,
            maxLines = maxLines,
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = textFieldValue.text,
                innerTextField = it,
                singleLine = singleLine,
                enabled = isEnabled,
                visualTransformation = VisualTransformation.None,
                trailingIcon = baseTrailingIcon,
                placeholder = placeholder,
                leadingIcon = baseLeadingIcon,
                interactionSource = remember { MutableInteractionSource() },
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 12.dp, bottom = 12.dp, start = 0.dp, end = 0.dp
                ),
            )
        }

        AnimatedVisibility(
            visible = errorMessage != null && userHasChangedText,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Text(
                text = errorMessage?.getString(context) ?: "",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

    }

}



data class TextFieldState(
    val textFieldValue: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val hasFocus: MutableState<Boolean> = mutableStateOf(false),
    val isUserChangedText: MutableState<Boolean> = mutableStateOf(false),
    val isValid: MutableState<Boolean> = mutableStateOf(false),
    val countryCode: MutableState<Pair<String, String>> = mutableStateOf(saudiArabia)
)
val saudiArabia = Pair("+966", "sa")

//endregion
