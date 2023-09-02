package com.trianglz.mimar.modules.cart.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.PrimarySecondaryButtonsRow
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopRoundedCorners
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteBottomSheet(
    state: () -> TextFieldState,
    closeClicked: () -> Unit,
    onSubmitBtnClicked: (String) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current


    val leaveNoteTextIconSpacer: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall.minus(2.dp)
        }
    }

//    LaunchedEffect(key1 = isBottomSheetOpened(),){
//        state().isUserChangedText.value = false
//    }

    val closeBtnClicked = remember {
        {
            keyboardController?.hide()
            closeClicked()
        }
    }

    val onPrimaryBtnClicked = {
        onSubmitBtnClicked(state().textFieldValue.value.text)
        closeBtnClicked()
    }



    BottomSheetTopRoundedCorners(
        containerModifier = { Modifier.padding(bottom = MaterialTheme.dimens.bottomNavigationHeight) }) {
        Column(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.screenGuideDefault
            )
        ) {

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))

            // Close Icon
            IconButton(
                modifier = Modifier
                    .size(MaterialTheme.dimens.iconSizeMedium)
                    .align(
                        Alignment.End
                    ), onClick = closeBtnClicked
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.close_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

            // Leave not row
            Row() {
                ImageFromRes(imageId = R.drawable.leave_note_icon, modifier = Modifier)

                Spacer(modifier = Modifier.padding(start = leaveNoteTextIconSpacer()))

                Text(
                    text = stringResource(id = R.string.leave_note),
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 1

                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.innerPaddingXLarge))

            BaseTextField(
                imeAction = ImeAction.Default,
                textState = state(),
                validator = TextInputValidator.FreeTextValidator(),
                hint = R.string.write_note_here,
                maxLines = Int.MAX_VALUE,

            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsSmall))

            PrimarySecondaryButtonsRow(
                modifier = Modifier,
                secondaryText = com.trianglz.core.R.string.cancel,
                primaryText = R.string.submit,
                onSecondaryBtnClicked = closeBtnClicked,
                isPrimaryBtnEnabled = { state().isValid.value },
                onPrimaryBtnClicked = onPrimaryBtnClicked
            )

        }
    }
}