package com.gildedgames.util.ui.graphics;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.graphics.Sprite.UV;

public class ResizableUVBehavior implements UVBehavior
{
	
	private final UV topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
	
	private final UV leftSide, topSide, rightSide, bottomSide;
	
	private final UV center;
	
	private final List<UVDimPair> drawnUVs = new ArrayList<UVDimPair>();
	
	private Dim2D lastAreaToDraw;
	
	public ResizableUVBehavior(UV cornersArea, UV verticalArea, UV horizontalArea)
	{
		this.topLeftCorner = cornersArea.clone().min(0, 0).flush();
		this.topRightCorner = cornersArea.clone().min(this.topLeftCorner.width() + horizontalArea.width(), 0).flush();
		this.bottomLeftCorner = cornersArea.clone().min(0, this.topLeftCorner.height() + verticalArea.height()).flush();
		this.bottomRightCorner = cornersArea.clone().min(this.topLeftCorner.width() + horizontalArea.width(), this.topLeftCorner.height() + verticalArea.height()).flush();
		
		this.leftSide = verticalArea.clone().min(0, this.topLeftCorner.height()).flush();
		this.rightSide = verticalArea.clone().min(this.topLeftCorner.width() + horizontalArea.width(), this.topRightCorner.height()).flush();
		
		this.topSide = horizontalArea.clone().min(this.topLeftCorner.width(), 0).flush();
		this.bottomSide = horizontalArea.clone().min(this.topLeftCorner.width(), this.topLeftCorner.height() + verticalArea.height()).flush();
		
		int centerWidth = this.topRightCorner.minU() - this.topLeftCorner.maxU();
		int centerHeight = this.bottomRightCorner.minV() - this.topLeftCorner.maxV();
		
		this.center = UV.build().min(this.topLeftCorner.maxU(), this.topLeftCorner.maxV()).area(centerWidth, centerHeight).flush();
	}

	@Override
	public List<UVDimPair> getDrawnUVsFor(Sprite sprite, Dim2DHolder areaToDraw)
	{
		return this.drawnUVs;
	}

	@Override
	public boolean shouldRecalculateUVs(Sprite sprite, Dim2DHolder areaToDraw)
	{
		return this.lastAreaToDraw == null || this.lastAreaToDraw.width() != areaToDraw.getDim().width() || this.lastAreaToDraw.height() != areaToDraw.getDim().height();
	}

	@Override
	public void recalculateUVs(Sprite sprite, Dim2DHolder areaToDraw)
	{
		this.drawnUVs.clear();
		
		Dim2D area = areaToDraw.getDim();
		
		this.drawnUVs.add(new UVDimPair(this.topLeftCorner, Dim2D.build().flush()));
		this.drawnUVs.add(new UVDimPair(this.topRightCorner, Dim2D.build().x(area.width() - this.topRightCorner.width()).flush()));
		
		this.drawnUVs.add(new UVDimPair(this.bottomLeftCorner, Dim2D.build().y(area.height() - this.topLeftCorner.height()).flush()));
		this.drawnUVs.add(new UVDimPair(this.bottomRightCorner, Dim2D.build().x(area.width() - this.topRightCorner.width()).y(area.height() - this.topLeftCorner.height()).flush()));
	
		for (int xStart = this.topLeftCorner.width(); xStart < area.width(); xStart += this.topSide.width())
		{
			int width = Math.min(this.topSide.width(), area.width() - xStart - this.topRightCorner.width());
			
			this.drawnUVs.add(new UVDimPair(this.topSide.clone().width(width).flush(), Dim2D.build().x(xStart).flush()));
			this.drawnUVs.add(new UVDimPair(this.bottomSide.clone().width(width).flush(), Dim2D.build().y(area.height() - this.topLeftCorner.height()).x(xStart).flush()));
		}
		
		for (int yStart = this.topLeftCorner.height(); yStart < area.height(); yStart += this.leftSide.height())
		{
			int height = Math.min(this.leftSide.height(), area.height() - yStart - this.bottomLeftCorner.height());
			
			this.drawnUVs.add(new UVDimPair(this.leftSide.clone().height(height).flush(), Dim2D.build().y(yStart).flush()));
			this.drawnUVs.add(new UVDimPair(this.rightSide.clone().height(height).flush(), Dim2D.build().x(area.width() - this.topLeftCorner.width()).y(yStart).flush()));
		}
		
		for (int xStart = this.topLeftCorner.width(); xStart < area.width(); xStart += this.center.width())
		{
			for (int yStart = this.topLeftCorner.height(); yStart < area.height(); yStart += this.center.height())
			{
				int width = Math.min(this.center.width(), area.width() - xStart - this.topRightCorner.width());
				int height = Math.min(this.center.height(), area.height() - yStart - this.bottomLeftCorner.height());
				
				this.drawnUVs.add(new UVDimPair(this.center.clone().width(width).height(height).flush(), Dim2D.build().x(xStart).y(yStart).flush()));
			}
		}
		
		this.lastAreaToDraw = areaToDraw.getDim();
	}

}
