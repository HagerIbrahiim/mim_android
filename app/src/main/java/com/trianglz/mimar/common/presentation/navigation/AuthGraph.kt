package com.trianglz.mimar.common.presentation.navigation

import com.ramcosta.composedestinations.annotation.NavGraph


@NavGraph(default = true)
annotation class AuthGraph(
    val start: Boolean = false
)