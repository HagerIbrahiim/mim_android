package com.trianglz.mimar.modules.services.presentation.model

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.models.AnnotatedTextModel

@Deprecated("Old validation method")
sealed class ServiceConflictType(
    val title: List<AnnotatedTextModel>,
    val description: List<AnnotatedTextModel>
) {
    object RescheduleConflict :
        ServiceConflictType(getRescheduleConflictTexts().first, getRescheduleConflictTexts().second)

    object EmployeeConflict :
        ServiceConflictType(getRescheduleConflictTexts().first, getRescheduleConflictTexts().second)

    object AddAnotherServiceConflict :
        ServiceConflictType(getRescheduleConflictTexts().first, getRescheduleConflictTexts().second)

    companion object {
        fun getRescheduleConflictTexts(): Pair<List<AnnotatedTextModel>, List<AnnotatedTextModel>> {
            val titleStyle: @Composable (color: Color) -> TextStyle =
                {
                    MaterialTheme.typography.subtitle2.copy(
                        fontSize = 18.sp,
                        color = it
                    )
                }

            val bodyStyle: @Composable (color: Color) -> TextStyle =
                {
                    MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        color = it
                    )
                }

            val title = listOf(
                AnnotatedTextModel(
                    StringWrapper(R.string.sorry),
                    style = { titleStyle.invoke(MaterialTheme.colors.secondary) }),
                AnnotatedTextModel(
                    StringWrapper(","),
                    style = { titleStyle.invoke(MaterialTheme.colors.secondary) }),
                AnnotatedTextModel(
                    StringWrapper(R.string.there_is_no_available_time_for_this_service),
                    style = { titleStyle.invoke(MaterialTheme.colors.primary) }),
            )

            val body = listOf(
                AnnotatedTextModel(
                    StringWrapper(R.string.try_to_check_another_day),
                    style = { bodyStyle.invoke(MaterialTheme.colors.onBackground) }),
            )

            return Pair(title, body)
        }
    }

}

