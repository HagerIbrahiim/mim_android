package com.trianglz.mimar.common.presentation.models

sealed class SelectionType {
    object SingleSelection: SelectionType()
    object MultiSelection: SelectionType()
}