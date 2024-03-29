/*
 * Will be removed since core is updated
 *
 *
 */

package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

enum class ScrollStrategy {
	EnterAlways {
		override fun create(
			offsetY: MutableState<Int>,
			toolbarState: CollapsingToolbarState
		): NestedScrollConnection =
			EnterAlwaysNestedScrollConnection(offsetY, toolbarState)
	},
	EnterAlwaysCollapsed {
		override fun create(
			offsetY: MutableState<Int>,
			toolbarState: CollapsingToolbarState
		): NestedScrollConnection =
			EnterAlwaysCollapsedNestedScrollConnection(offsetY, toolbarState)
	},
	ExitUntilCollapsed {
		override fun create(
			offsetY: MutableState<Int>,
			toolbarState: CollapsingToolbarState
		): NestedScrollConnection =
			ExitUntilCollapsedNestedScrollConnection(toolbarState)
	};

	internal abstract fun create(
		offsetY: MutableState<Int>,
		toolbarState: CollapsingToolbarState
	): NestedScrollConnection
}

private class ScrollDelegate(
	private val offsetY: MutableState<Int>
) {
	private var scrollToBeConsumed: Float = 0f

	fun doScroll(delta: Float) {
		val scroll = scrollToBeConsumed + delta
		val scrollInt = scroll.toInt()

		scrollToBeConsumed = scroll - scrollInt

		offsetY.value += scrollInt
	}
}

internal class EnterAlwaysNestedScrollConnection(
	private val offsetY: MutableState<Int>,
	private val toolbarState: CollapsingToolbarState
): NestedScrollConnection {
	private val scrollDelegate = ScrollDelegate(offsetY)

	override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
		val dy = available.y

		val toolbar = toolbarState.height.toFloat()
		val offset = offsetY.value.toFloat()

		// -toolbarHeight <= offsetY + dy <= 0
		val consume = if(dy < 0) {
			val toolbarConsumption = toolbarState.dispatchRawDelta(dy)
			val remaining = dy - toolbarConsumption
			val offsetConsumption = remaining.coerceAtLeast(-toolbar - offset)
			scrollDelegate.doScroll(offsetConsumption)

			toolbarConsumption + offsetConsumption
		}else{
			val offsetConsumption = dy.coerceAtMost(-offset)
			scrollDelegate.doScroll(offsetConsumption)

			val toolbarConsumption = toolbarState.dispatchRawDelta(dy - offsetConsumption)

			offsetConsumption + toolbarConsumption
		}

		return Offset(0f, consume)
	}
}

internal class EnterAlwaysCollapsedNestedScrollConnection(
	private val offsetY: MutableState<Int>,
	private val toolbarState: CollapsingToolbarState
): NestedScrollConnection {
	private val scrollDelegate = ScrollDelegate(offsetY)

	override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
		val dy = available.y

		println("onPreScroll: $dy, $source")

		val consumed = if(dy > 0) { // expanding: offset -> body -> toolbar
			val offsetConsumption = dy.coerceAtMost(-offsetY.value.toFloat())
			scrollDelegate.doScroll(offsetConsumption)

			offsetConsumption
		}else{ // collapsing: toolbar -> offset -> body
			val toolbarConsumption = toolbarState.dispatchRawDelta(dy)
			val offsetConsumption = (dy - toolbarConsumption).coerceAtLeast(-toolbarState.height.toFloat() - offsetY.value)

			scrollDelegate.doScroll(offsetConsumption)

			toolbarConsumption + offsetConsumption
		}

		return Offset(0f, consumed)
	}

	override fun onPostScroll(
		consumed: Offset,
		available: Offset,
		source: NestedScrollSource
	): Offset {
		val dy = available.y

		return if(dy > 0) {
			Offset(0f, toolbarState.dispatchRawDelta(dy))
		}else{
			Offset.Zero
		}
	}

	override suspend fun onPreFling(available: Velocity): Velocity {
		println("fling: ${available.y}")

		val left = toolbarState.fling(available.y)

		return Velocity(0f, available.y - left)
	}
}

internal class ExitUntilCollapsedNestedScrollConnection(
	private val toolbarState: CollapsingToolbarState
): NestedScrollConnection {
	override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
		val dy = available.y
		val consume = if(dy < 0) { // collapsing: toolbar -> body
			toolbarState.dispatchRawDelta(dy)
		}else{
			0f
		}

		println("onPreScroll: ${available.y} $consume $source")

		return Offset(0f, consume)
	}

	override fun onPostScroll(
		consumed: Offset,
		available: Offset,
		source: NestedScrollSource
	): Offset {
		val dy = available.y

		val consume = if(dy > 0) { // expanding: body -> toolbar
			toolbarState.dispatchRawDelta(dy)
		}else{
			0f
		}

		return Offset(0f, consume)
	}

	override suspend fun onPreFling(available: Velocity): Velocity {
		println("fling: ${available.y}")

		val left = toolbarState.fling(available.y)

		return Velocity(0f, available.y - left)
	}
}
