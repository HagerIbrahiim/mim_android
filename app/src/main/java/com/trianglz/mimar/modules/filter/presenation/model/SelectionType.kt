package com.trianglz.mimar.modules.filter.presenation.model

sealed class SelectionType {
    object SingleSelection: SelectionType()
    object MultiSelection: SelectionType()
}