/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 1:04 PM
 *
 */

package com.trianglz.mimar.common.presentation.extensions

import androidx.compose.ui.Modifier

/**
 * Instead of checking if a value is not null while providing a Modifier; this can be
 * done on the fly
 */
inline fun <T : Any> Modifier.ifNotNull(value: T?, modifierBuilder: (T) -> Modifier): Modifier =
    then(if (value != null) modifierBuilder(value) else Modifier)

inline fun Modifier.ifTrue(predicate: Boolean, modifierBuilder: () -> Modifier) =
    then(if (predicate) modifierBuilder() else Modifier)