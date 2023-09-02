package com.trianglz.mimar.modules.categories.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel

/**
 * Created by hassankhamis on 04,January,2023
 */

@Composable
fun CategoryItem(
    imageModifier: Modifier = Modifier,
    parentModifier: Modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    categoryUIModel: () -> CategoryUIModel,
    onCategoryItemClicked: (Int) -> Unit,
) {



    Column(
        modifier = parentModifier
            .clip(MaterialTheme.shapes.large)
            .clickable {
                onCategoryItemClicked(categoryUIModel().id)
            }
            .padding(bottom = MaterialTheme.dimens.spaceBetweenItemsMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsMedium),
        horizontalAlignment = Alignment.CenterHorizontally
        
    ) {
        val contentScale = remember {
            mutableStateOf(ContentScale.Crop)
        }

        val painter = rememberAsyncImagePainter(
            categoryUIModel().image,
            error = painterResource(
                id = R.drawable.placeholder
            ),
            onError = {
                contentScale.value = ContentScale.Inside
            }
        )
        Image(
            painter = painter,
            modifier = imageModifier
                .background(
                    shape = MaterialTheme.shapes.large,
                    color = categoryUIModel().color.invoke()
                )
                .clip(MaterialTheme.shapes.large)
                .shimmer(categoryUIModel().showShimmer)
                .padding(MaterialTheme.dimens.innerPaddingMedium)
                .clip(MaterialTheme.shapes.large),
            contentScale = contentScale.value,
            contentDescription = null
        )
        Text(
            text = categoryUIModel().name,
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .shimmer(categoryUIModel().showShimmer, shimmerWidth = 0.8f),
        )
    }
}