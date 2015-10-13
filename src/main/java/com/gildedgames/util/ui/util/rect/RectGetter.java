package com.gildedgames.util.ui.util.rect;

import com.gildedgames.util.ui.data.rect.ModDim2D;
import com.gildedgames.util.ui.data.rect.Rect;

public abstract class RectGetter<S> extends RectSeeker<S>
{

	private ModDim2D assembledDim = new ModDim2D();

	public RectGetter()
	{

	}

	public RectGetter(S seekFrom)
	{
		this.seekFrom = seekFrom;
	}

	@Override
	public final ModDim2D dim()
	{
		if ((this.assembledDim == null || this.shouldReassemble()))
		{
			this.assembledDim.set(this.assembleRect());
		}

		return super.dim();
	}

	/**
	 * Will only be called when dimHasChanged() returns true.
	 * @return
	 */
	public abstract Rect assembleRect();

	/**
	 * Calculations should be kept small here to check if the Dim2D object that will be assembled is different.
	 * @return
	 */
	public abstract boolean shouldReassemble();

}
