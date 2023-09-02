package com.trianglz.mimar.modules.countries.presentation.model

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel
import com.trianglz.mimar.R
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CountryUIModel(
    val id: Int,
    val name: String="",
    val dialCode: String ="",
    val shortCode: String="",
    val selected: Boolean = false,
    override val showShimmer: Boolean = false,

    ) : Parcelable, PaginatedModel, ShimmerModel {
    override val uniqueId: Int
        get() = id


    companion object {

        fun getShimmerList(): SnapshotStateList<CountryUIModel> {
            val list: SnapshotStateList<CountryUIModel> = SnapshotStateList()
            repeat(20) {
                list.add(
                    CountryUIModel(
                        id = it,
                        shortCode = "sa",
                        showShimmer = true,
                    )
                )
            }
            return list
        }

        fun getCountryImageResourceId(context: Context, shortCode: String,): Int {
            return try {
                val resources = context.resources
                val shortCodeToLowercase = shortCode.lowercase(Locale(Locales.ENGLISH.code))

                resources.getIdentifier(
                    if (shortCode != "DO") shortCodeToLowercase else shortCodeToLowercase.plus("_"),
                    "drawable",
                    context.packageName
                )

            } catch (e: Exception) {
                R.drawable.kw
            }
        }

    }



}



