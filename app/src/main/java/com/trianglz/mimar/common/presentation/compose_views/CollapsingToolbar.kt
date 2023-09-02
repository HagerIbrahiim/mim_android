/*
 * Will be removed since core is updated
 *
 *
 */

package com.trianglz.mimar.common.presentation.compose_views

import androidx.annotation.FloatRange
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Stable
class CollapsingToolbarState(
	initial: Int = Int.MAX_VALUE,
	private val flingBehavior: FlingBehavior
): ScrollableState {
	private val scrollableState = ScrollableState {
		val consume = if(it < 0) {
			max(minHeight.toFloat() - height, it)
		}else{
			min(maxHeight.toFloat() - height, it)
		}

		val current = consume + deferredConsumption
		val currentInt = current.toInt()

		if(current.absoluteValue > 0) {
			height += currentInt
			deferredConsumption = current - currentInt
		}

		consume
	}

	/**
	 * [height] indicates current height of the toolbar.
	 */
	var height: Int by mutableStateOf(initial)
		private set

	/**
	 * [minHeight] indicates the minimum height of the collapsing toolbar. The toolbar
	 * may collapse its height to [minHeight] but not smaller. This size is determined by
	 * the smallest child.
	 */
	var minHeight: Int
		get() = minHeightState
		internal set(value) {
			minHeightState = value

			if(height < value) {
				height = value
			}
		}

	/**
	 * [maxHeight] indicates the maximum height of the collapsing toolbar. The toolbar
	 * may expand its height to [maxHeight] but not larger. This size is determined by
	 * the largest child.
	 */
	var maxHeight: Int
		get() = maxHeightState
		internal set(value) {
			maxHeightState = value

			if(value < height) {
				height = value
			}
		}

	private var maxHeightState by mutableStateOf(Int.MAX_VALUE)
	private var minHeightState by mutableStateOf(0)

	val progress: Float
		@FloatRange(from = 0.0, to = 1.0)
		get() =
			if(minHeight == maxHeight) {
				0f
			}else{
				((height - minHeight).toFloat() / (maxHeight - minHeight)).coerceIn(0f, 1f)
			}

	private var deferredConsumption: Float = 0f

	/**
	 * @return consumed scroll value is returned
	 */
	@Deprecated(
		message = "use dispatchRawScroll() instead",
		ReplaceWith("dispatchRawDelta(value)")
	)
	fun feedScroll(value: Float): Float =
		dispatchRawDelta(value)

	internal suspend fun fling(velocity: Float): Float {
		var left = velocity

		scroll {
			with(flingBehavior) {
				left = performFling(velocity)
			}
		}

		return left
	}

	override val isScrollInProgress: Boolean
		get() = scrollableState.isScrollInProgress

	override fun dispatchRawDelta(delta: Float): Float =
		scrollableState.dispatchRawDelta(delta)

	override suspend fun scroll(
		scrollPriority: MutatePriority,
		block: suspend ScrollScope.() -> Unit
	) = scrollableState.scroll(scrollPriority, block)
}

@Composable
fun rememberCollapsingToolbarState(
	initial: Int = Int.MAX_VALUE
): CollapsingToolbarState {
	val flingBehavior = ScrollableDefaults.flingBehavior()

	return remember {
		CollapsingToolbarState(
			initial = initial,
			flingBehavior = flingBehavior
		)
	}
}

@Composable
fun CollapsingToolbar(
	modifier: Modifier = Modifier,
	collapsingToolbarState: CollapsingToolbarState,
	content: @Composable CollapsingToolbarScope.() -> Unit
) {
	val measurePolicy = remember(collapsingToolbarState) {
		CollapsingToolbarMeasurePolicy(collapsingToolbarState)
	}

	Layout(
		content = { CollapsingToolbarScopeInstance.content() },
		measurePolicy = measurePolicy,
		modifier = modifier
			.clipToBounds()
	)
}

private class CollapsingToolbarMeasurePolicy(
	private val collapsingToolbarState: CollapsingToolbarState
): MeasurePolicy {
	override fun MeasureScope.measure(
		measurables: List<Measurable>,
		constraints: Constraints
	): MeasureResult {
		val placeStrategy = arrayOfNulls<Any>(measurables.size)

		var width = 0

		var minHeight = Int.MAX_VALUE
		var maxHeight = 0

		val placeables = measurables.mapIndexed { i, measurable ->
			// measure with no height constraints
			val placeable = measurable.measure(
				constraints.copy(
					minWidth = 0,
					minHeight = 0,
					maxHeight = Constraints.Infinity
				)
			)
			placeStrategy[i] = measurable.parentData

			width = max(placeable.width, width)

			minHeight = min(minHeight, placeable.height)
			maxHeight = max(maxHeight, placeable.height)

			placeable
		}

		width = width.coerceIn(constraints.minWidth, constraints.maxWidth)

		collapsingToolbarState.also {
			it.minHeight = minHeight
			it.maxHeight = maxHeight
		}

		val height = collapsingToolbarState.height
		return layout(width, height) {
			val progress = collapsingToolbarState.progress

			placeables.forEachIndexed { i, placeable ->
				val strategy = placeStrategy[i]
				if(strategy is CollapsingToolbarData) {
					strategy.progressListener?.onProgressUpdate(progress)
				}

				when(strategy) {
					is CollapsingToolbarRoadData -> {
						val collapsed = strategy.whenCollapsed
						val expanded = strategy.whenExpanded

						val collapsedOffset = collapsed.align(
							size = IntSize(placeable.width, placeable.height),
							space = IntSize(width, height),
							// TODO LayoutDirection
							layoutDirection = LayoutDirection.Ltr
						)

						val expandedOffset = expanded.align(
							size = IntSize(placeable.width, placeable.height),
							space = IntSize(width, height),
							// TODO LayoutDirection
							layoutDirection = LayoutDirection.Ltr
						)

						val offset = collapsedOffset + (expandedOffset - collapsedOffset) * progress

						placeable.place(offset.x, offset.y)
					}
					is CollapsingToolbarParallaxData ->
						placeable.place(
							x = 0,
							y = -((maxHeight - minHeight) * (1 - progress) * strategy.ratio).roundToInt()
						)
					else -> placeable.place(0, 0)
				}
			}
		}
	}
}

interface CollapsingToolbarScope {
	fun Modifier.progress(listener: ProgressListener): Modifier

	fun Modifier.road(whenCollapsed: Alignment, whenExpanded: Alignment): Modifier

	fun Modifier.parallax(ratio: Float = 0.2f): Modifier

	fun Modifier.pin(): Modifier
}

object CollapsingToolbarScopeInstance: CollapsingToolbarScope {
	override fun Modifier.progress(listener: ProgressListener): Modifier {
		return this.then(ProgressUpdateListenerModifier(listener))
	}

	override fun Modifier.road(whenCollapsed: Alignment, whenExpanded: Alignment): Modifier {
		return this.then(RoadModifier(whenCollapsed, whenExpanded))
	}

	override fun Modifier.parallax(ratio: Float): Modifier {
		return this.then(ParallaxModifier(ratio))
	}

	override fun Modifier.pin(): Modifier {
		return this.then(PinModifier())
	}
}

internal class RoadModifier(
	private val whenCollapsed: Alignment,
	private val whenExpanded: Alignment
): ParentDataModifier {
	override fun Density.modifyParentData(parentData: Any?): Any {
		return CollapsingToolbarRoadData(
			this@RoadModifier.whenCollapsed, this@RoadModifier.whenExpanded,
			(parentData as? CollapsingToolbarData)?.progressListener
		)
	}
}

internal class ParallaxModifier(
	private val ratio: Float
): ParentDataModifier {
	override fun Density.modifyParentData(parentData: Any?): Any {
		return CollapsingToolbarParallaxData(ratio, (parentData as? CollapsingToolbarData)?.progressListener)
	}
}

internal class PinModifier: ParentDataModifier {
	override fun Density.modifyParentData(parentData: Any?): Any {
		return CollapsingToolbarPinData((parentData as? CollapsingToolbarData)?.progressListener)
	}
}

internal class ProgressUpdateListenerModifier(
	private val listener: ProgressListener
): ParentDataModifier {
	override fun Density.modifyParentData(parentData: Any?): Any {
		return CollapsingToolbarProgressData(listener)
	}
}

fun interface ProgressListener {
	fun onProgressUpdate(value: Float)
}

internal sealed class CollapsingToolbarData(
	var progressListener: ProgressListener?
)

internal class CollapsingToolbarProgressData(
	progressListener: ProgressListener?
): CollapsingToolbarData(progressListener)

internal class CollapsingToolbarRoadData(
	var whenCollapsed: Alignment,
	var whenExpanded: Alignment,
	progressListener: ProgressListener? = null
): CollapsingToolbarData(progressListener)

internal class CollapsingToolbarPinData(
	progressListener: ProgressListener? = null
): CollapsingToolbarData(progressListener)

internal class CollapsingToolbarParallaxData(
	var ratio: Float,
	progressListener: ProgressListener? = null
): CollapsingToolbarData(progressListener)
