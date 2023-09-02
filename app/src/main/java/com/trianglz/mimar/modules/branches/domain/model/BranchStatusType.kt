package com.trianglz.mimar.modules.branches.domain.model

import com.trianglz.mimar.R

sealed class BranchStatusType (val key: String, val name: Int){
    object Active: BranchStatusType("active", R.string.active)
    object Deactivated: BranchStatusType("deactivated",R.string.deactivated )
    object Suspended: BranchStatusType("suspended", R.string.suspended)

    companion object {
        fun String?.toBranchStatusType(): BranchStatusType {
            return when(this) {
                Active.key -> Active
                Deactivated.key -> Deactivated
                Suspended.key -> Suspended
                else -> Deactivated
            }
        }
    }
}
