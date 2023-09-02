package com.trianglz.mimar.modules.branch_view_all.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.branches.presentation.model.BranchDetailsUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class BranchViewAllEvent : BaseEvent {
    object RefreshScreen : BranchViewAllEvent()
    object OnMapClicked : BranchViewAllEvent()
    data class ToggleBranchFavoriteClicked(val item: BranchUIModel) : BranchViewAllEvent()
    data class OtherBranchClicked(val branchId: Int) : BranchViewAllEvent()
    data class EmployeeItemClicked(val employeeId: Int) : BranchViewAllEvent()

}

sealed class BranchViewAllState : BaseState {
    data class OpenMap(val branch: BranchDetailsUIModel?) : BranchViewAllState()
    data class OpenBranchScreen(val branchId: Int) : BranchViewAllState()
    data class OpenEmployeeDetailsScreen(val employeeId: Int, val branchName: String) : BranchViewAllState()

}

data class BranchViewAllViewState(
    val branchDetails: MutableState<BranchDetailsUIModel?> = mutableStateOf(null),
    val branchId: MutableState<Int?> = mutableStateOf(null),
    var otherBranches: SnapshotStateList<BranchUIModel>? = mutableStateListOf(),
    var branchStaffMembers: SnapshotStateList<EmployeeUIModel>? = mutableStateListOf(),
    val isLoading: MutableState<Boolean?> = mutableStateOf(null),
    val isOtherBranchesExpanded: MutableState<Boolean> = mutableStateOf(false),
    val isStaffExpanded: MutableState<Boolean> = mutableStateOf(false),
    val isWorkingHoursExpanded: MutableState<Boolean> = mutableStateOf(false),
    val isSupportedAreasExpanded: MutableState<Boolean> = mutableStateOf(false),
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false)
) : BaseViewState