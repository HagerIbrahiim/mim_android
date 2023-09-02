package com.trianglz.mimar.common.presentation.navigation

import com.ramcosta.composedestinations.annotation.NavGraph


@NavGraph(default = false)
annotation class MainGraph(
    val start: Boolean = false
)